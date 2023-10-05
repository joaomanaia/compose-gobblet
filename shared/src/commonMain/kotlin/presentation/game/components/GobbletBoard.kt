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
import androidx.compose.ui.unit.dp
import model.GobbletBoardItem
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
internal fun GobbletBoard(
    modifier: Modifier = Modifier,
    boardGobblets: List<GobbletBoardItem?> = emptyList(),
    onItemClick: (index: Int) -> Unit = {},
) {
    val boardSize = sqrt(boardGobblets.size.toDouble()).roundToInt()

    val gridColor = MaterialTheme.colorScheme.surfaceVariant

    val board2d = boardGobblets.chunked(boardSize)

    Column(
        modifier = Modifier
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item == null) {
                            EmptyComponent(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(1.dp),
                                onClick = { onItemClick(rowIndex + columnIndex * boardSize) }
                            )
                        } else {
                            GobbletComponent(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                tier = item.tier,
                                player = item.player,
                                onClick = { onItemClick(rowIndex + columnIndex * boardSize) }
                            )
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
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick
    ) {}
}

private fun Modifier.drawBackgroundGrid(
    gridSize: Int = 3,
    gridColor: Color,
    strokeWidth: Float = 2f,
) = drawBehind {
    // Draw the horizontal lines
    for (i in 1 until gridSize) {
        drawLine(
            color = gridColor,
            start = Offset(0f, size.height * i / gridSize),
            end = Offset(size.width, size.height * i / gridSize),
            strokeWidth = strokeWidth,
        )
    }

    // Draw the vertical lines
    for (i in 1 until gridSize) {
        drawLine(
            color = gridColor,
            start = Offset(size.width * i / gridSize, 0f),
            end = Offset(size.width * i / gridSize, size.height),
            strokeWidth = strokeWidth,
        )
    }
}
