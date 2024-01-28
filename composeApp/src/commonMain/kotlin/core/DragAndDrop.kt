/*
 * Original code from https://github.com/cp-radhika-s/Drag_and_drop_jetpack_compose
 */
package core

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun LongPressDraggable(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }

    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()

            if (state.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val offset = (state.dragPosition + state.dragOffset)

                            scaleX = 1.3f
                            scaleY = 1.3f
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                            translationX = offset.x.minus(targetSize.width / 2)
                            translationY = offset.y.minus(targetSize.height / 2)
                        }.onGloballyPositioned {
                            targetSize = it.size
                        }
                ) {
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    enabled: Boolean = true,
    content: @Composable (() -> Unit)
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }.pointerInput(enabled) { // Check for changes of enabled state, if the state changes this block will restart and check if it can drag
                if (!enabled) return@pointerInput

                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        currentState.dataToDrop = dataToDrop
                        currentState.isDragging = true
                        currentState.dragPosition = currentPosition + it
                        currentState.draggableComposable = content
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        currentState.isDragging = false
                        currentState.dragOffset = Offset.Zero
                    },
                    onDragCancel = {
                        currentState.dragOffset = Offset.Zero
                        currentState.isDragging = false
                    }
                )
            }
    ) {
        content()
    }
}

@Composable
fun <T> DropTarget(
    modifier: Modifier = Modifier,
    onDroppedOnTarget: (data: T) -> Unit,
    content: @Composable (BoxScope.(isInBound: Boolean, draggingData: T?) -> Unit)
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    val isDragging = dragInfo.isDragging

    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned {
            it.boundsInWindow().let { rect ->
                isCurrentDropTarget = isDragging && rect.contains(dragPosition + dragOffset)
            }
        }
    ) {
        val data = dragInfo.dataToDrop as T?
        content(isCurrentDropTarget, data)

        if (isCurrentDropTarget && !dragInfo.isDragging) {
            data?.let { onDroppedOnTarget(it) }
        }
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}
