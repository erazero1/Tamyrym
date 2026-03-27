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
)

// ─────────────────────────────────────────────
//  Layout result
// ─────────────────────────────────────────────

/**
 * Positions for every entity in the tree.
 *
 * - Person IDs   → top-left corner of the person card.
 * - Union IDs    → bottom-center of the parent block (connector anchor for lines).
 * - [cyclicLinks] — union/person IDs detected to form graph cycles; rendered
 *                    with a dashed "cyclic" line instead of a normal branch.
 */
internal data class LayoutResult(
    val positions: Map<String, Offset>,
    val cyclicLinks: Set<String>,
)

// ─────────────────────────────────────────────
//  Engine
// ─────────────────────────────────────────────

/**
 * Sugiyama-inspired two-pass layout engine.
 *
 * Pass 1 (bottom-up)  — compute the minimum width each sub-tree requires.
 * Pass 2 (top-down)   — assign absolute (x, y) coordinates.
 *
 * Time complexity: O(V + E).
 */
internal class TreeLayoutEngine(private val config: LayoutConfig = LayoutConfig()) {

    // ── internal state (reset on every calculate() call) ──────────────────

    private val personMap = mutableMapOf<String, PersonInfo>()
    private val unionMap = mutableMapOf<String, Union>()
    private val subtreeWidths = mutableMapOf<String, Float>()    // keyed by personId
    private val cyclicLinks = mutableSetOf<String>()

    // ── public API ────────────────────────────────────────────────────────

    fun calculate(tree: TreeGraph): LayoutResult {
        reset()
        tree.persons.forEach { personMap[it.id] = it }
        tree.unions.forEach { unionMap[it.id] = it }

        // Pass 1 — subtree widths, bottom-up
        tree.rootPersonIds.forEach { rootId ->
            calculateSubtreeWidth(rootId, callStack = mutableSetOf())
        }

        // Pass 2 — absolute coordinates, top-down
        val positions = mutableMapOf<String, Offset>()
        var globalX = 0f

        tree.rootPersonIds.forEach { rootId ->
            val treeWidth = subtreeWidths[rootId] ?: config.nodeWidth
            assignCoordinates(
                personId = rootId,
                xStart = globalX,
                generation = 0,
                result = positions,
                callStack = mutableSetOf()
            )
            globalX += treeWidth + config.horizontalSpacing
        }

        return LayoutResult(positions, cyclicLinks.toSet())
    }

    // ── Pass 1: compute subtree width ─────────────────────────────────────

    /**
     * Returns (and caches) the total width that the sub-tree rooted at
     * [personId] needs.  Cycle detection uses [callStack].
     */
    private fun calculateSubtreeWidth(
        personId: String,
        callStack: MutableSet<String>,
    ): Float {
        // Cycle detected — mark and return a leaf-sized stub
        if (personId in callStack) {
            cyclicLinks.add(personId)
            return config.nodeWidth
        }

        // Already computed
        subtreeWidths[personId]?.let { return it }

        callStack.add(personId)

        val selfWidth = blockWidth(personId)
        val children = mainUnion(personId)?.childrenIds ?: emptyList()

        val width = if (children.isEmpty()) {
            selfWidth
        } else {
            val childrenWidth = children.sumOf { childId ->
                calculateSubtreeWidth(childId, callStack).toDouble()
            }.toFloat() + config.horizontalSpacing * (children.size - 1).coerceAtLeast(0)

            maxOf(selfWidth, childrenWidth)
        }

        callStack.remove(personId)
        subtreeWidths[personId] = width
        return width
    }

    // ── Pass 2: assign coordinates ────────────────────────────────────────

    /**
     * Places [personId] and its entire sub-tree inside the horizontal band
     * [xStart .. xStart + subtreeWidth].
     *
     * Y is derived strictly from [generation]:
     *   Y = generation × (nodeHeight + verticalSpacing)
     */
    private fun assignCoordinates(
        personId: String,
        xStart: Float,
        generation: Int,
        result: MutableMap<String, Offset>,
        callStack: MutableSet<String>,
    ) {
        // Guard against cycles & already-placed nodes
        if (personId in callStack) return
        if (personId in result) return

        callStack.add(personId)

        val subtreeWidth = subtreeWidths[personId] ?: config.nodeWidth
        val centerX = xStart + subtreeWidth / 2f
        val y = generation * (config.nodeHeight + config.verticalSpacing)
        val union = mainUnion(personId)
        val hasSpouse = union != null && union.person2Id.isNotEmpty()

        // ── Place person (and optional spouse) ───────────────────────────

        if (hasSpouse) {
            // Block width = 2×nodeWidth + spouseSpacing, centered in subtree
            val totalBlock = config.nodeWidth * 2 + config.spouseSpacing
            val blockStartX = centerX - totalBlock / 2f

            result[personId] = Offset(blockStartX, y)
            result[union.person2Id] =
                Offset(blockStartX + config.nodeWidth + config.spouseSpacing, y)

            // Union anchor: bottom-center of the couple block
            result[union.id] = Offset(centerX, y + config.nodeHeight)

        } else {
            // Single parent or lone person
            result[personId] = Offset(centerX - config.nodeWidth / 2f, y)

            // Still record a union anchor if a single-parent union exists
            if (union != null) {
                result[union.id] = Offset(centerX, y + config.nodeHeight)
            }
        }

        // ── Place children ────────────────────────────────────────────────

        val children = union?.childrenIds ?: emptyList()
        if (children.isNotEmpty()) {
            val childrenTotalWidth =
                children.sumOf { subtreeWidths[it]?.toDouble() ?: config.nodeWidth.toDouble() }
                    .toFloat() + config.horizontalSpacing * (children.size - 1).coerceAtLeast(0)

            var childX = centerX - childrenTotalWidth / 2f

            children.forEach { childId ->
                if (childId !in callStack) {
                    assignCoordinates(
                        personId = childId,
                        xStart = childX,
                        generation = generation + 1,
                        result = result,
                        callStack = callStack
                    )
                    childX += (subtreeWidths[childId]
                        ?: config.nodeWidth) + config.horizontalSpacing
                }
            }
        }

        callStack.remove(personId)
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    /** Width of the visual block for [personId] (single card or couple). */
    private fun blockWidth(personId: String): Float {
        val union = mainUnion(personId)
        return if (union != null && union.person2Id.isNotEmpty()) {
            config.nodeWidth * 2 + config.spouseSpacing
        } else {
            config.nodeWidth
        }
    }

    /** Resolves the main union for [personId], or null. */
    private fun mainUnion(personId: String): Union? {
        val person = personMap[personId] ?: return null
        return person.mainUnionId.takeIf { it.isNotEmpty() }?.let { unionMap[it] }
    }

    private fun reset() {
        personMap.clear()
        unionMap.clear()
        subtreeWidths.clear()
        cyclicLinks.clear()
    }
}