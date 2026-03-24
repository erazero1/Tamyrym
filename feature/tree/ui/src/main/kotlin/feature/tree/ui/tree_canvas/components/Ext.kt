package feature.tree.ui.tree_canvas.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import feature.tree.ui.tree_canvas.model.LayoutConfig

/**
 * Draws the classic genealogy branch:
 *
 *   parentAnchor
 *        │
 *        ├──────┬──────┐
 *        │      │      │
 *     child1  child2  child3
 */
internal fun DrawScope.drawFamilyLines(
    parentAnchor: Offset,
    childTops: List<Offset>,
    config: LayoutConfig,
    isCyclic: Boolean,
    color: Color,
) {
    val strokeWidth = if (isCyclic) 2f else 1.8f
    val pathEffect = if (isCyclic) PathEffect.dashPathEffect(floatArrayOf(10f, 6f)) else null

    val junctionY = parentAnchor.y + config.verticalSpacing / 2f

    // Vertical stem from parent anchor down to junction
    drawLine(
        color = color,
        start = Offset(x = parentAnchor.x, y = parentAnchor.y - config.nodeHeight / 2),
        end = Offset(parentAnchor.x, junctionY),
        strokeWidth = strokeWidth,
        pathEffect = pathEffect
    )

    if (childTops.size == 1) {
        // Single child — straight vertical line, no horizontal bar
        drawLine(
            color = color,
            start = Offset(childTops[0].x, junctionY),
            end = childTops[0],
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
        // Connect stem to child if off-center
        if (childTops[0].x != parentAnchor.x) {
            drawLine(
                color = color,
                start = Offset(parentAnchor.x, junctionY),
                end = Offset(childTops[0].x, junctionY),
                strokeWidth = strokeWidth,
                pathEffect = pathEffect
            )
        }
    } else {
        // Multiple children — horizontal bar at junctionY
        val leftX = childTops.minOf { it.x }
        val rightX = childTops.maxOf { it.x }

        drawLine(
            color = color,
            start = Offset(leftX, junctionY),
            end = Offset(rightX, junctionY),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )

        // Vertical drop to each child
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
}