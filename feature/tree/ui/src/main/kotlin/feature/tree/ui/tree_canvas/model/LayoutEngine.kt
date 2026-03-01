package feature.tree.ui.tree_canvas.model

import feature.tree.domain.model.TreeGraph
import feature.tree.domain.model.Union


class LayoutEngine {
    private val nodeWidth = 300f
    private val nodeHeight = 150f
    private val horizontalSpacing = 80f
    private val verticalSpacing = 200f
    private val spouseSpacing = 320f

    private val uiNodes = mutableMapOf<String, UiPersonNode>()
    private val levelWidths = mutableMapOf<Int, Float>()

    fun processGraph(graph: TreeGraph): Map<String, UiPersonNode> {
        uiNodes.clear()
        levelWidths.clear()

        graph.persons.forEach { person ->
            uiNodes[person.id] = UiPersonNode(person = person)
        }

        val childrenMap = graph.links.groupBy({ it.parentId }, { it.childId })

        val unionMap = graph.unions.flatMap { listOf(it.person1Id to it, it.person2Id to it) }
            .groupBy({ it.first }, { it.second })

        val allChildIds = graph.links.map { it.childId }.toSet()
        val roots = graph.persons.filter { it.id !in allChildIds }

        roots.forEach { root ->
            calculatePositions(
                personId = root.id,
                depth = 0,
                childrenMap = childrenMap,
                unionMap = unionMap,
                visited = mutableSetOf()
            )
        }

        return uiNodes
    }

    private fun calculatePositions(
        personId: String,
        depth: Int,
        childrenMap: Map<String, List<String>>,
        unionMap: Map<String, List<Union>>,
        visited: MutableSet<String>,
    ): Float {
        if (personId in visited) return uiNodes[personId]?.x ?: 0f
        visited.add(personId)

        val node = uiNodes[personId] ?: return 0f
        node.generationLevel = depth
        node.y = depth * (nodeHeight + verticalSpacing)

        val childrenIds = childrenMap[personId] ?: emptyList()
        val unions = unionMap[personId] ?: emptyList()

        if (childrenIds.isEmpty() && unions.isEmpty()) {
            val currentEdge = levelWidths.getOrDefault(depth, 0f)
            node.x = currentEdge
            levelWidths[depth] = currentEdge + nodeWidth + horizontalSpacing
            return node.x
        }

        var childrenXSum = 0f
        var processedChildren = 0

        childrenIds.forEach { childId ->
            if (childId !in visited) {
                childrenXSum += calculatePositions(
                    childId,
                    depth + 1,
                    childrenMap,
                    unionMap,
                    visited
                )
                processedChildren++
            }
        }

        val baseX = if (processedChildren > 0) {
            childrenXSum / processedChildren
        } else {
            levelWidths.getOrDefault(depth, 0f)
        }

        node.x = maxOf(baseX, levelWidths.getOrDefault(depth, 0f))
        levelWidths[depth] = node.x + nodeWidth + horizontalSpacing

        unions.forEach { union ->
            val spouseId = if (union.person1Id == personId) union.person2Id else union.person1Id
            if (spouseId !in visited) {
                val spouseNode = uiNodes[spouseId]
                if (spouseNode != null) {
                    visited.add(spouseId)
                    spouseNode.generationLevel = depth
                    spouseNode.y = node.y

                    spouseNode.x = levelWidths.getOrDefault(depth, node.x + spouseSpacing)
                    levelWidths[depth] = spouseNode.x + nodeWidth + horizontalSpacing
                }
            }
        }

        return node.x
    }
}