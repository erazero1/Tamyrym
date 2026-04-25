package feature.tree.ui.tree_canvas.model

import androidx.compose.ui.geometry.Offset
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.model.Union
import java.util.ArrayDeque
import kotlin.math.max

internal data class LayoutConfig(
    val nodeWidth: Float = 160f,
    val nodeHeight: Float = 80f,
    val horizontalSpacing: Float = 64f,
    val verticalSpacing: Float = 80f,
    val spouseSpacing: Float = 40f,
    val unionSpacing: Float = 100f,
)

internal data class LayoutResult(
    val positions: Map<String, Offset>,
    val dummyNodes: Map<String, List<Offset>> = emptyMap(),
    val cyclicLinks: Set<String> = emptySet(),
    val ranks: Map<String, Int> = emptyMap(),
)

internal class TreeLayoutEngine(
    private val config: LayoutConfig = LayoutConfig()
) {
    private lateinit var personUnions: Map<String, List<Union>>
    private val personWidths = mutableMapOf<String, Float>()
    private val unionWidths = mutableMapOf<String, Float>()
    private val ranks = mutableMapOf<String, Int>()
    private val cyclicLinks = mutableSetOf<String>()
    private val placedPersons = mutableSetOf<String>()
    private val placedUnions = mutableSetOf<String>()
    private val dummyNodesResult = mutableMapOf<String, List<Offset>>()

    fun calculate(tree: TreeGraph): LayoutResult {
        if (tree.persons.isEmpty()) {
            return LayoutResult(emptyMap())
        }

        initMaps(tree)
        computeRanks(tree)

        personWidths.clear()
        unionWidths.clear()
        cyclicLinks.clear()
        placedPersons.clear()
        placedUnions.clear()
        dummyNodesResult.clear()

        val roots = resolveRoots(tree)
        roots.forEach { calculatePersonWidth(it, mutableSetOf()) }

        val positions = mutableMapOf<String, Offset>()
        var currentX = 0f

        roots.forEach { rootId ->
            if (rootId !in placedPersons) {
                val width = personWidths[rootId] ?: config.nodeWidth
                placePerson(
                    personId = rootId,
                    xStart = currentX,
                    result = positions,
                    stack = mutableSetOf()
                )
                currentX += width + config.horizontalSpacing
            }
        }

        return LayoutResult(
            positions = positions,
            dummyNodes = dummyNodesResult,
            cyclicLinks = cyclicLinks,
            ranks = ranks.toMap()
        )
    }

    private fun resolveRoots(tree: TreeGraph): List<String> {
        val childrenIds = tree.unions.flatMap { it.childrenIds }.toSet()

        val explicitRoots = tree.rootPersonIds.filter { id ->
            tree.persons.any { it.id == id }
        }

        if (explicitRoots.isNotEmpty()) return explicitRoots

        val structuralRoots = tree.persons
            .filter { it.id !in childrenIds }
            .map { it.id }

        return if (structuralRoots.isNotEmpty()) structuralRoots else listOf(tree.persons.first().id)
    }

    private fun initMaps(tree: TreeGraph) {
        val unionsByPerson = mutableMapOf<String, MutableList<Union>>()

        tree.unions.forEach { union ->
            unionsByPerson.getOrPut(union.person1Id) { mutableListOf() }.add(union)
            if (union.person2Id.isNotBlank()) {
                unionsByPerson.getOrPut(union.person2Id) { mutableListOf() }.add(union)
            }
        }

        personUnions = unionsByPerson
    }

    private fun computeRanks(tree: TreeGraph) {
        ranks.clear()

        val queue = ArrayDeque<String>()
        val roots = resolveRoots(tree)

        roots.forEach { rootId ->
            ranks[rootId] = 0
            queue.add(rootId)
        }

        while (queue.isNotEmpty()) {
            val personId = queue.removeFirst()
            val currentRank = ranks[personId] ?: 0

            personUnions[personId].orEmpty().forEach { union ->
                ranks.putIfAbsent(union.id, currentRank)

                val childRank = currentRank + 1
                union.childrenIds.forEach { childId ->
                    val existing = ranks[childId]
                    if (existing == null || childRank > existing) {
                        ranks[childId] = childRank
                        queue.add(childId)
                    }
                }
            }
        }
    }

    private fun calculatePersonWidth(personId: String, stack: MutableSet<String>): Float {
        personWidths[personId]?.let { return it }

        if (!stack.add(personId)) {
            cyclicLinks.add(personId)
            return config.nodeWidth
        }

        val unions = personUnions[personId].orEmpty()
        val width = when {
            unions.isEmpty() -> config.nodeWidth

            unions.size == 1 -> {
                calculateUnionWidth(unions.first(), stack)
            }

            else -> {
                var total = 0f
                unions.forEachIndexed { index, union ->
                    total += calculateUnionWidth(union, stack)
                    if (index < unions.lastIndex) {
                        total += config.unionSpacing
                    }
                }
                max(config.nodeWidth, total)
            }
        }

        stack.remove(personId)
        personWidths[personId] = width
        return width
    }

    private fun calculateUnionWidth(union: Union, stack: MutableSet<String>): Float {
        unionWidths[union.id]?.let { return it }

        val spouseBlockWidth = if (union.person2Id.isNotBlank()) {
            config.nodeWidth * 2f + config.spouseSpacing
        } else {
            config.nodeWidth
        }

        var childrenWidth = 0f
        union.childrenIds.forEachIndexed { index, childId ->
            childrenWidth += calculatePersonWidth(childId, stack)
            if (index < union.childrenIds.lastIndex) {
                childrenWidth += config.horizontalSpacing
            }
        }

        val width = max(spouseBlockWidth, childrenWidth)
        unionWidths[union.id] = width
        return width
    }

    private fun placePerson(
        personId: String,
        xStart: Float,
        result: MutableMap<String, Offset>,
        stack: MutableSet<String>
    ) {
        if (personId in placedPersons) return
        if (!stack.add(personId)) {
            cyclicLinks.add(personId)
            return
        }

        placedPersons.add(personId)

        val y = (ranks[personId] ?: 0) * (config.nodeHeight + config.verticalSpacing)
        val personWidth = personWidths[personId] ?: config.nodeWidth
        val unions = personUnions[personId].orEmpty()

        val primaryUnion = unions.firstOrNull { it.id !in placedUnions }
        val spouseBlockWidth = when {
            primaryUnion == null -> config.nodeWidth
            primaryUnion.person2Id.isNotBlank() -> config.nodeWidth * 2f + config.spouseSpacing
            else -> config.nodeWidth
        }

        val cardX = if (primaryUnion == null) {
            xStart + (personWidth - config.nodeWidth) / 2f
        } else {
            xStart + (personWidth - spouseBlockWidth) / 2f
        }

        result[personId] = Offset(cardX, y)

        unions.forEach { union ->
            if (union.id in placedUnions) return@forEach
            placedUnions.add(union.id)
            placeUnion(
                union = union,
                currentPersonId = personId,
                result = result,
                stack = stack
            )
        }

        stack.remove(personId)
    }

    private fun placeUnion(
        union: Union,
        currentPersonId: String,
        result: MutableMap<String, Offset>,
        stack: MutableSet<String>
    ) {
        val currentPos = result[currentPersonId] ?: return

        val spouseId = when (currentPersonId) {
            union.person1Id -> union.person2Id
            union.person2Id -> union.person1Id
            else -> union.person2Id.ifBlank { union.person1Id }
        }

        if (spouseId.isNotBlank() && spouseId !in placedPersons) {
            val spouseX = currentPos.x + config.nodeWidth + config.spouseSpacing
            placePerson(spouseId, spouseX, result, stack)
        }

        val spousePos = result[spouseId]
        val unionX = when {
            spousePos != null -> {
                ((currentPos.x + config.nodeWidth / 2f) + (spousePos.x + config.nodeWidth / 2f)) / 2f
            }

            union.person2Id.isNotBlank() -> currentPos.x + config.nodeWidth + config.spouseSpacing / 2f
            else -> currentPos.x + config.nodeWidth / 2f
        }

        val unionY = currentPos.y + config.nodeHeight / 2f
        result[union.id] = Offset(unionX, unionY)

        if (union.childrenIds.isEmpty()) return

        var childrenWidth = 0f
        union.childrenIds.forEachIndexed { index, childId ->
            childrenWidth += personWidths[childId] ?: config.nodeWidth
            if (index < union.childrenIds.lastIndex) {
                childrenWidth += config.horizontalSpacing
            }
        }

        var childX = unionX - childrenWidth / 2f
        union.childrenIds.forEach { childId ->
            val childWidth = personWidths[childId] ?: config.nodeWidth
            placePerson(childId, childX, result, stack)
            generateDummyNodes(union.id, childId, unionX, result)
            childX += childWidth + config.horizontalSpacing
        }
    }

    private fun generateDummyNodes(
        unionId: String,
        childId: String,
        unionX: Float,
        result: Map<String, Offset>
    ) {
        val childPos = result[childId] ?: return
        val startRank = ranks[unionId] ?: return
        val endRank = ranks[childId] ?: return

        if (endRank - startRank <= 1) return

        val childCenterX = childPos.x + config.nodeWidth / 2f
        val dummies = mutableListOf<Offset>()

        for (r in (startRank + 1) until endRank) {
            val t = (r - startRank).toFloat() / (endRank - startRank).toFloat()
            val x = unionX + (childCenterX - unionX) * t
            val y = r * (config.nodeHeight + config.verticalSpacing) + config.nodeHeight / 2f
            dummies.add(Offset(x, y))
        }

        dummyNodesResult["$unionId->$childId"] = dummies
    }
}