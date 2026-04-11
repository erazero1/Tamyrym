package feature.tree.ui.tree_canvas.model

import androidx.compose.ui.geometry.Offset
import feature.tree.domain.model.PersonInfo
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.model.Union

internal data class LayoutConfig(
    val nodeWidth: Float = 160f,
    val nodeHeight: Float = 80f,
    val horizontalSpacing: Float = 64f,
    val verticalSpacing: Float = 40f,
    val spouseSpacing: Float = 80f,
    val unionSpacing: Float = 120f, // Расстояние между разными союзами одного человека
)

/**
 * Результат layout-вычислений.
 *
 * @property positions Карта ID → координата top-left для person/union, или промежуточная точка для dummy nodes
 * @property dummyNodes Карта ID фиктивного узла → его координата (для отрисовки линий через промежуточные слои)
 * @property cyclicLinks Множество ID узлов, участвующих в циклах
 * @property ranks Карта ID узла → номер поколения (слоя)
 */
internal data class LayoutResult(
    val positions: Map<String, Offset>,
    val dummyNodes: Map<String, List<Offset>> = emptyMap(), // ID ребра → список промежуточных точек
    val cyclicLinks: Set<String> = emptySet(),
    val ranks: Map<String, Int> = emptyMap(),
)

/**
 * Полноценная реализация Sugiyama framework для генеалогических древ.
 *
 * Фазы алгоритма:
 * 1. Ранжирование (Ranking) — назначение поколений всем узлам
 * 2. Добавление фиктивных узлов (Dummy Nodes) — для рёбер через несколько слоёв
 * 3. Вычисление ширин поддеревьев (Bottom-up)
 * 4. Назначение координат (Top-down) с обработкой множественных союзов и консангвинитета
 *
 * Особенности для генеалогии:
 * - Поддержка множественных браков (союзы располагаются горизонтально)
 * - Обработка инбридинга (consanguinity) — общие предки не дублируются
 * - Группировка детей под соответствующим союзом
 */
internal class TreeLayoutEngine(private val config: LayoutConfig = LayoutConfig()) {

    // ─────────────────────────────────────────────────────────────────────────
    // Внутреннее состояние (сбрасывается на каждый вызов calculate())
    // ─────────────────────────────────────────────────────────────────────────

    private lateinit var personMap: HashMap<String, PersonInfo>
    private lateinit var unionMap: HashMap<String, Union>

    // Для каждого person: список всех союзов, где он участвует (как person1 или person2)
    private lateinit var personUnions: HashMap<String, List<Union>>

    // Ширина поддерева для каждого узла (person или union)
    private lateinit var subtreeWidths: HashMap<String, Float>

    // Множество циклических связей
    private val cyclicLinks = mutableSetOf<String>()

    // Ранг (поколение) каждого узла
    private lateinit var ranks: HashMap<String, Int>

    // Множество уже размещённых узлов (для обработки консангвинитета)
    private val placedNodes = mutableSetOf<String>()

    // Временные координаты для вычисления средней позиции при консангвинитете
    private val pendingPositions = mutableMapOf<String, MutableList<Offset>>()

    // ─────────────────────────────────────────────────────────────────────────
    // Публичный API
    // ─────────────────────────────────────────────────────────────────────────

    fun calculate(tree: TreeGraph): LayoutResult {
        reset(tree)

        // Фаза 1: Ранжирование — определяем поколение для каждого узла
        computeRanks(tree)

        // Фаза 2: Добавляем фиктивные узлы для рёбер через несколько слоёв
        val dummyNodes = computeDummyNodes(tree)

        // Фаза 3: Вычисляем ширины поддеревьев (bottom-up)
        tree.rootPersonIds.forEach { rootId ->
            calculateSubtreeWidth(rootId, callStack = mutableSetOf())
        }

        // Фаза 4: Назначаем координаты (top-down)
        val positions = mutableMapOf<String, Offset>()
        var globalX = 0f

        tree.rootPersonIds.forEach { rootId ->
            val treeWidth = subtreeWidths[rootId] ?: config.nodeWidth
            assignCoordinates(
                personId = rootId,
                xStart = globalX,
                parentCenterX = null,
                result = positions,
                callStack = mutableSetOf()
            )
            globalX += treeWidth + config.horizontalSpacing
        }

        // Обработка консангвинитета: усредняем позиции для узлов с несколькими родителями
        val finalPositions = resolveConsanguinity(positions)

        return LayoutResult(
            positions = finalPositions,
            dummyNodes = dummyNodes,
            cyclicLinks = cyclicLinks.toSet(),
            ranks = ranks.toMap()
        )
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Фаза 1: Ранжирование (Ranking / Layer Assignment)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Вычисляет ранг (поколение) для каждого узла в графе.
     *
     * Алгоритм: обход в ширину от корней, затем корректировка "вверх"
     * для узлов с родителями в разных поколениях.
     *
     * Для генеалогии: rank(person) = max(rank(всех союзов где он ребёнок)) + 1
     *                rank(union) = max(rank(родителей))
     *                (или rank(parent1) если известен)
     */
    private fun computeRanks(tree: TreeGraph) {
        // Инициализируем ранги
        ranks = HashMap(tree.persons.size + tree.unions.size)

        // Сначала назначаем ранги корням
        tree.rootPersonIds.forEach { ranks[it] = 0 }

        // Обходим в ширину от корней вниз к потомкам
        val queue = ArrayDeque<String>()
        queue.addAll(tree.rootPersonIds)
        val visited = mutableSetOf<String>()

        while (queue.isNotEmpty()) {
            val personId = queue.removeFirst()
            if (personId in visited) continue
            visited.add(personId)

            val personRank = ranks[personId] ?: 0
            val unions = personUnions[personId] ?: emptyList()

            unions.forEach { union ->
                // Ранг союза = ранг родителя (или max рангов обоих родителей)
                val otherParentId =
                    if (union.person1Id == personId) union.person2Id else union.person1Id
                val otherParentRank = if (otherParentId.isNotEmpty()) ranks[otherParentId] else null

                val unionRank = if (otherParentRank != null) {
                    maxOf(personRank, otherParentRank)
                } else {
                    personRank
                }

                // Обновляем ранг союза если он уже был назначен (берём максимум)
                val currentUnionRank = ranks[union.id]
                ranks[union.id] = if (currentUnionRank != null) {
                    maxOf(currentUnionRank, unionRank)
                } else {
                    unionRank
                }

                // Дети союза идут на следующий уровень
                val childRank = (ranks[union.id] ?: unionRank) + 1
                union.childrenIds.forEach { childId ->
                    val currentChildRank = ranks[childId]
                    ranks[childId] = if (currentChildRank != null) {
                        // Уже есть ранг от другого родителя — берём максимум
                        maxOf(currentChildRank, childRank)
                    } else {
                        childRank
                    }
                    queue.addLast(childId)
                }
            }
        }

        // Корректировка: убеждаемся, что дети всегда ниже родителей
        var changed = true
        var iterations = 0
        val maxIterations = tree.persons.size + tree.unions.size

        while (changed && iterations < maxIterations) {
            changed = false
            iterations++

            tree.unions.forEach { union ->
                val unionRank = ranks[union.id] ?: 0
                val parent1Rank = ranks[union.person1Id] ?: 0
                val parent2Rank =
                    if (union.person2Id.isNotEmpty()) ranks[union.person2Id] ?: 0 else parent1Rank

                // Союз должен быть на уровне max(родителей)
                val requiredUnionRank = maxOf(parent1Rank, parent2Rank)
                if (unionRank != requiredUnionRank) {
                    ranks[union.id] = requiredUnionRank
                    changed = true
                }

                // Дети должны быть ниже союза
                val requiredChildRank = requiredUnionRank + 1
                union.childrenIds.forEach { childId ->
                    val childRank = ranks[childId] ?: requiredChildRank
                    if (childRank < requiredChildRank) {
                        ranks[childId] = requiredChildRank
                        changed = true
                    }
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Фаза 2: Фиктивные узлы (Dummy Nodes)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Вычисляет промежуточные точки для рёбер, пересекающих несколько слоёв.
     *
     * Возвращает карту: ID союза → список Offset для отрисовки ломаной линии
     * через промежуточные поколения.
     */
    private fun computeDummyNodes(tree: TreeGraph): Map<String, List<Offset>> {
        val dummyNodes = mutableMapOf<String, MutableList<Offset>>()

        tree.unions.forEach { union ->
            val unionRank = ranks[union.id] ?: return@forEach

            union.childrenIds.forEach { childId ->
                val childRank = ranks[childId] ?: return@forEach
                val span = childRank - unionRank

                if (span > 1) {
                    // Требуются фиктивные узлы на уровнях unionRank+1 ... childRank-1
                    val dummies = mutableListOf<Offset>()

                    // X будет вычислен позже, пока сохраняем только Y-координаты
                    // Фактические X будут определены при назначении координат
                    for (level in unionRank + 1 until childRank) {
                        val y =
                            level * (config.nodeHeight + config.verticalSpacing) + config.nodeHeight / 2
                        // X будет заполнен позже через специальную логику
                        dummies.add(Offset(0f, y))
                    }

                    dummyNodes.getOrPut("${union.id}->$childId") { mutableListOf() }.addAll(dummies)
                }
            }
        }

        return dummyNodes
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Фаза 3: Вычисление ширин поддеревьев (Bottom-up)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Рекурсивно вычисляет ширину поддерева для personId.
     *
     * Для множественных союзов: ширина = сумма ширин всех союзов + расстояния между ними
     * Для каждого союза: ширина = max(ширина блока супругов, сумма ширин детей)
     */
    private fun calculateSubtreeWidth(
        personId: String,
        callStack: MutableSet<String>,
    ): Float {
        // Обнаружение цикла
        if (personId in callStack) {
            cyclicLinks.add(personId)
            return config.nodeWidth
        }

        // Уже вычислено
        subtreeWidths[personId]?.let { return it }

        callStack.add(personId)

        val unions = personUnions[personId]?.sortedBy { it.generation } ?: emptyList()

        val width = if (unions.isEmpty()) {
            // Листовой узел без союзов
            config.nodeWidth
        } else {
            // Суммируем ширину всех союзов этого человека
            var totalWidth = 0f
            unions.forEachIndexed { index, union ->
                val unionWidth = calculateUnionSubtreeWidth(union, callStack)
                totalWidth += unionWidth
                if (index < unions.size - 1) {
                    totalWidth += config.unionSpacing
                }
            }
            totalWidth
        }

        callStack.remove(personId)
        subtreeWidths[personId] = width
        return width
    }

    /**
     * Вычисляет ширину поддерева для конкретного союза.
     */
    private fun calculateUnionSubtreeWidth(
        union: Union,
        callStack: MutableSet<String>,
    ): Float {
        // Ширина блока супругов
        val spouseBlockWidth = calculateSpouseBlockWidth(union)

        // Ширина детей
        val children = union.childrenIds
        val childrenWidth = if (children.isEmpty()) {
            0f
        } else {
            var width = 0f
            children.forEachIndexed { index, childId ->
                width += calculateSubtreeWidth(childId, callStack)
                if (index < children.size - 1) {
                    width += config.horizontalSpacing
                }
            }
            width
        }

        return maxOf(spouseBlockWidth, childrenWidth)
    }

    /**
     * Ширина визуального блока супругов для союза.
     */
    private fun calculateSpouseBlockWidth(union: Union): Float {
        val hasSpouse = union.person2Id.isNotEmpty()
        return if (hasSpouse) {
            config.nodeWidth * 2 + config.spouseSpacing
        } else {
            config.nodeWidth
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Фаза 4: Назначение координат (Top-down)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Рекурсивно назначает координаты для personId и всех его потомков.
     *
     * @param personId ID текущего человека
     * @param xStart Начальная X-координата доступного пространства
     * @param parentCenterX X-координата центра родительского блока (для консангвинитета)
     * @param result Мапа для накопления результатов
     * @param callStack Стек вызовов для обнаружения циклов
     */
    private fun assignCoordinates(
        personId: String,
        xStart: Float,
        parentCenterX: Float?,
        result: MutableMap<String, Offset>,
        callStack: MutableSet<String>,
    ) {
        // Защита от циклов и повторной обработки
        if (personId in callStack) return

        // Обработка консангвинитета: узел уже размещён через другую ветвь
        if (personId in placedNodes) {
            // Сохраняем альтернативную позицию для усреднения
            val existingPos = result[personId]
            if (existingPos != null && parentCenterX != null) {
                pendingPositions.getOrPut(personId) { mutableListOf() }.add(existingPos)

                // Вычисляем "желаемую" позицию от текущего родителя
                val desiredX = parentCenterX - config.nodeWidth / 2f
                val rank = ranks[personId] ?: 0
                val y = rank * (config.nodeHeight + config.verticalSpacing)
                pendingPositions[personId]?.add(Offset(desiredX, y))
            }
            return
        }

        callStack.add(personId)
        placedNodes.add(personId)

        val subtreeWidth = subtreeWidths[personId] ?: config.nodeWidth
        val centerX = xStart + subtreeWidth / 2f
        val rank = ranks[personId] ?: 0
        val y = rank * (config.nodeHeight + config.verticalSpacing)

        // Получаем все союзы этого человека, сортируем по поколению союза
        val unions = personUnions[personId]?.sortedBy { it.generation } ?: emptyList()

        if (unions.isEmpty()) {
            // Одиночный узел без союзов
            result[personId] = Offset(centerX - config.nodeWidth / 2f, y)
        } else {
            // Размещаем все союзы горизонтально
            var currentX = xStart
            unions.forEachIndexed { index, union ->
                val unionWidth = subtreeWidths[union.id] ?: calculateSpouseBlockWidth(union)

                // Размещаем блок супругов для этого союза
                placeUnionBlock(union, currentX, y, rank, result)

                // Размещаем детей этого союза под ним
                placeChildren(union, currentX, y, rank, result, callStack)

                currentX += unionWidth
                if (index < unions.size - 1) {
                    currentX += config.unionSpacing
                }
            }
        }

        callStack.remove(personId)
    }

    /**
     * Размещает блок супругов для союза.
     */
    private fun placeUnionBlock(
        union: Union,
        xStart: Float,
        y: Float,
        rank: Int,
        result: MutableMap<String, Offset>,
    ) {
        val hasSpouse = union.person2Id.isNotEmpty()
        val blockWidth = calculateSpouseBlockWidth(union)
        val centerX = xStart + blockWidth / 2f

        // Сохраняем позицию союза (якорь для линий)
        result[union.id] = Offset(centerX, y + config.nodeHeight)

        if (hasSpouse) {
            // Два супруга рядом
            val blockStartX = centerX - blockWidth / 2f
            result[union.person1Id] = Offset(blockStartX, y)
            result[union.person2Id] =
                Offset(blockStartX + config.nodeWidth + config.spouseSpacing, y)
        } else {
            // Один родитель
            result[union.person1Id] = Offset(centerX - config.nodeWidth / 2f, y)
        }
    }

    /**
     * Рекурсивно размещает детей союза.
     */
    private fun placeChildren(
        union: Union,
        unionXStart: Float,
        parentY: Float,
        parentRank: Int,
        result: MutableMap<String, Offset>,
        callStack: MutableSet<String>,
    ) {
        val children = union.childrenIds
        if (children.isEmpty()) return

        val unionWidth = subtreeWidths[union.id] ?: calculateSpouseBlockWidth(union)
        val unionCenterX = unionXStart + unionWidth / 2f

        // Вычисляем общую ширину детей
        var childrenTotalWidth = 0f
        children.forEachIndexed { index, childId ->
            childrenTotalWidth += subtreeWidths[childId] ?: config.nodeWidth
            if (index < children.size - 1) {
                childrenTotalWidth += config.horizontalSpacing
            }
        }

        // Центрируем детей под союзом
        var childX = unionCenterX - childrenTotalWidth / 2f

        children.forEach { childId ->
            val childWidth = subtreeWidths[childId] ?: config.nodeWidth

            assignCoordinates(
                personId = childId,
                xStart = childX,
                parentCenterX = unionCenterX,
                result = result,
                callStack = callStack
            )

            childX += childWidth + config.horizontalSpacing
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Обработка консангвинитета (Consanguinity)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Разрешает позиции для узлов с инбридингом (несколько родителей).
     *
     * Для узлов, которые имеют несколько "ожидаемых" позиций от разных родителей,
     * вычисляем среднюю позицию и обновляем всех потомков соответственно.
     */
    private fun resolveConsanguinity(positions: MutableMap<String, Offset>): Map<String, Offset> {
        if (pendingPositions.isEmpty()) return positions

        val finalPositions = positions.toMutableMap()

        pendingPositions.forEach { (personId, positionList) ->
            if (positionList.size >= 2) {
                // Вычисляем среднюю позицию
                val avgX = positionList.map { it.x }.average().toFloat()
                val y = positionList.first().y // Y должен быть одинаковым

                // Обновляем позицию узла
                finalPositions[personId] = Offset(avgX, y)

                // TODO: Здесь можно добавить корректировку позиций потомков,
                // если требуется сохранить относительное расположение поддерева
            }
        }

        return finalPositions
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Вспомогательные методы
    // ─────────────────────────────────────────────────────────────────────────

    private fun reset(tree: TreeGraph) {
        // Оптимизация: предварительное выделение HashMap с нужной ёмкостью
        val personCount = tree.persons.size
        val unionCount = tree.unions.size

        personMap = HashMap(personCount * 2)
        unionMap = HashMap(unionCount * 2)
        subtreeWidths = HashMap((personCount + unionCount) * 2)
        personUnions = HashMap(personCount * 2)

        // Заполняем мапы
        tree.persons.forEach { personMap[it.id] = it }
        tree.unions.forEach { unionMap[it.id] = it }

        // Строим индекс союзов для каждого человека
        tree.unions.forEach { union ->
            personUnions.getOrPut(union.person1Id) { mutableListOf() }.let {
                if (it is MutableList) it.add(union) else personUnions[union.person1Id] =
                    (it + union)
            }
            if (union.person2Id.isNotEmpty()) {
                personUnions.getOrPut(union.person2Id) { mutableListOf() }.let {
                    if (it is MutableList) it.add(union) else personUnions[union.person2Id] =
                        (it + union)
                }
            }
        }

        // Сортируем союзы по generation для каждого человека
        personUnions.replaceAll { _, unions ->
            unions.sortedBy { it.generation }
        }

        cyclicLinks.clear()
        placedNodes.clear()
        pendingPositions.clear()
    }
}