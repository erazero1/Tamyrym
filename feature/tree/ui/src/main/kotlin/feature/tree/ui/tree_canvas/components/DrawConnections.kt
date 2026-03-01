package feature.tree.ui.tree_canvas.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import feature.tree.domain.model.Link
import feature.tree.domain.model.Union
import feature.tree.ui.tree_canvas.model.UiPersonNode

@Composable
fun DrawConnections(
    links: List<Link>,
    unions: List<Union>,
    uiNodes: Map<String, UiPersonNode>,
    nodeWidth: Float = 300f,
    nodeHeight: Float = 150f,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val lineColor = Color.DarkGray
        val strokeWidth = 6f
        val strokeStyle = Stroke(width = strokeWidth)

        unions.forEach { union ->
            val p1 = uiNodes[union.person1Id]
            val p2 = uiNodes[union.person2Id]

            if (p1 != null && p2 != null) {
                val leftPerson = if (p1.x < p2.x) p1 else p2
                val rightPerson = if (p1.x < p2.x) p2 else p1

                val startX = leftPerson.x + nodeWidth
                val startY = leftPerson.y + (nodeHeight / 2f)
                val endX = rightPerson.x
                val endY = rightPerson.y + (nodeHeight / 2f)

                drawLine(
                    color = lineColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth
                )
            }
        }

        links.forEach { link ->
            val parent = uiNodes[link.parentId]
            val child = uiNodes[link.childId]

            if (parent != null && child != null) {
                val startX = parent.x + (nodeWidth / 2f)
                val startY = parent.y + nodeHeight

                val endX = child.x + (nodeWidth / 2f)
                val endY = child.y

                val midY = startY + ((endY - startY) / 2f)

                val path = Path().apply {
                    moveTo(startX, startY)
                    lineTo(startX, midY)
                    lineTo(endX, midY)
                    lineTo(endX, endY)
                }

                drawPath(
                    path = path,
                    color = lineColor,
                    style = strokeStyle
                )
            }
        }
    }
}