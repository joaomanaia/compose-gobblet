package presentation.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import model.GobbletBoardItem
import model.GobbletTier
import core.DropTarget
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
internal fun GobbletBoardComponent(
    modifier: Modifier = Modifier,
    boardGobblets: List<GobbletBoardItem?> = emptyList(),
    onItemDrop: (
        index: Int,
        tier: GobbletTier
    ) -> Unit = { _, _ -> }
) {
    val boardSize = sqrt(boardGobblets.size.toDouble()).roundToInt()

    val gridColor = MaterialTheme.colorScheme.surfaceVariant

    val board2d = boardGobblets.chunked(boardSize)

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBackgroundGrid(
                gridSize = boardSize,
                gridColor = gridColor,
            ),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        board2d.forEachIndexed { columnIndex, rows ->
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                rows.forEachIndexed { rowIndex, item ->
                    DropTarget<GobbletTier>(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onDroppedOnTarget = { droppedTier ->
                            val canBeStacked = droppedTier canBeStackedOn item?.tier
                            if (!canBeStacked) return@DropTarget

                            onItemDrop(rowIndex + columnIndex * boardSize, droppedTier)
                        },
                    ) { isInBound, draggingTier ->
                        val canBeStacked = draggingTier != null && draggingTier canBeStackedOn item?.tier

                        val surfaceColor = when {
                            isInBound && canBeStacked -> MaterialTheme.colorScheme.primary
                            isInBound && !canBeStacked -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp),
                            tonalElevation = if (isInBound) 2.dp else 0.dp,
                            color = surfaceColor
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                if (item == null) {
                                    EmptyComponent(
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                } else {
                                    GobbletComponent(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .padding(16.dp)
                                            .fillMaxSize(),
                                        tier = item.tier,
                                        player = item.player
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyComponent(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 0.dp
) {
    Surface(
        modifier = modifier,
        tonalElevation = tonalElevation
    ) {}
}

private fun Modifier.drawBackgroundGrid(
    gridSize: Int = 3,
    gridColor: Color,
    strokeWidth: Dp = 3.dp
) = drawBehind {
    // Draw the horizontal lines
    for (i in 1 until gridSize) {
        drawLine(
            color = gridColor,
            start = Offset(0f, size.height * i / gridSize),
            end = Offset(size.width, size.height * i / gridSize),
            strokeWidth = strokeWidth.toPx(),
        )
    }

    // Draw the vertical lines
    for (i in 1 until gridSize) {
        drawLine(
            color = gridColor,
            start = Offset(size.width * i / gridSize, 0f),
            end = Offset(size.width * i / gridSize, size.height),
            strokeWidth = strokeWidth.toPx(),
        )
    }
}
