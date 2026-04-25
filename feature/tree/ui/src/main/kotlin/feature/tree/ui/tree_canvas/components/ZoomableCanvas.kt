package feature.tree.ui.tree_canvas.components

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import core.presentation.R

internal interface ZoomableCanvasScope {
    val scale: Float
    val offset: Offset
    val canvasSize: IntSize
}

@Composable
internal fun ZoomableCanvas(
    modifier: Modifier = Modifier,
    content: @Composable ZoomableCanvasScope.() -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val zoomableScope = remember(scale, offset, canvasSize) {
        object : ZoomableCanvasScope {
            override val scale: Float = scale
            override val offset: Offset = offset
            override val canvasSize: IntSize = canvasSize
        }
    }
    Box(
        modifier = modifier
            .onSizeChanged { canvasSize = it }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    scale = (scale * zoom).coerceIn(0.3f, 3f)

                    // Calculate the fractional change in scale
                    val fraction = scale / oldScale

                    // Adjust offsets to keep the centroid stationary under the fingers
                    offset = Offset(
                        x = offset.x * fraction + centroid.x * (1 - fraction) + pan.x,
                        y = offset.y * fraction + centroid.y * (1 - fraction) + pan.y
                    )
                }
            }
    ) {
        zoomableScope.content()
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SmallFloatingActionButton(
                onClick = {
                    scale = (scale * 1.2f).coerceIn(0.3f, 3f)
                }
            ) {
                Icon(painter = painterResource(R.drawable.add_24px), contentDescription = null)
            }

            SmallFloatingActionButton(
                onClick = {
                    scale = (scale / 1.2f).coerceIn(0.3f, 3f)
                }
            ) {
                Icon(painter = painterResource(R.drawable.ic_remove), contentDescription = null)
            }
        }
    }
}
