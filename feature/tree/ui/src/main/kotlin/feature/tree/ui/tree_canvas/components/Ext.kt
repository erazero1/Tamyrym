package feature.tree.ui.tree_canvas.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import feature.tree.ui.tree_canvas.model.LayoutConfig

/**
 * Draws the family branch from a union point down to children.
 * parentAnchor is the point where the line starts (the junction on the marriage line).
 * childTops are the top-center points of each child's card or the first dummy node.
 */

internal fun DrawScope.drawFamilyLines(
    parentAnchor: Offset,
    childTops: List<Offset>,
    config: LayoutConfig,
    isCyclic: Boolean,
    color: Color,
) {
    if (childTops.isEmpty()) return

    val strokeWidth = 2f
    val pathEffect = if (isCyclic) {
        PathEffect.dashPathEffect(floatArrayOf(10f, 6f))
    } else {
        null
    }

    // Низ карточки родителя + половина расстояния до детей.
    val junctionY = parentAnchor.y + config.nodeHeight / 2f + config.verticalSpacing / 2f

    drawLine(
        color = color,
        start = parentAnchor,
        end = Offset(parentAnchor.x, junctionY),
        strokeWidth = strokeWidth,
        pathEffect = pathEffect
    )

    if (childTops.size == 1) {
        val child = childTops.first()

        if (child.x != parentAnchor.x) {
            drawLine(
                color = color,
                start = Offset(parentAnchor.x, junctionY),
                end = Offset(child.x, junctionY),
                strokeWidth = strokeWidth,
                pathEffect = pathEffect
            )
        }

        drawLine(
            color = color,
            start = Offset(child.x, junctionY),
            end = child,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
        return
    }

    val minX = minOf(childTops.minOf { it.x }, parentAnchor.x)
    val maxX = maxOf(childTops.maxOf { it.x }, parentAnchor.x)

    drawLine(
        color = color,
        start = Offset(minX, junctionY),
        end = Offset(maxX, junctionY),
        strokeWidth = strokeWidth,
        pathEffect = pathEffect
    )

    childTops.forEach { childTop ->
        drawLine(
            color = color,
            start = Offset(childTop.x, junctionY),
            end = childTop,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}