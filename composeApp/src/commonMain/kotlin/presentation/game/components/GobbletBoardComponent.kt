package presentation.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.DropTarget
import core.presentation.theme.TierColors
import model.Player
import model.GobbletBoardItem
import model.GameResult
import model.GobbletTier
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
internal fun GobbletBoardComponent(
    modifier: Modifier = Modifier,
    boardGobblets: List<GobbletBoardItem?> = emptyList(),
    gameResult: GameResult? = null,
    currentPlayer: Player,
    tierColors: TierColors = TierColors.defaultTierColors(),
    colors: BoardColors = BoardDefaults.colors(
        tierColors = tierColors
    ),
    onItemDrop: (
        index: Int,
        tier: GobbletTier
    ) -> Unit = { _, _ -> }
) {
    val boardSize = remember(boardGobblets) {
        sqrt(boardGobblets.size.toDouble()).roundToInt()
    }

    val board2d = remember(boardGobblets, boardSize) {
        boardGobblets.chunked(boardSize)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBackgroundGrid(
                gridSize = boardSize,
                gridColor = colors.gridColor,
            ).then(
                if (gameResult != null && gameResult is GameResult.Winner) {
                    Modifier.drawWinnerLine(
                        winner = gameResult,
                        color = colors.winnerLineColor(player = gameResult.player).value,
                    )
                } else Modifier
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

                        val surfaceColor = colors.surfaceColor(
                            isInBound = isInBound,
                            canBeStacked = canBeStacked,
                            player = currentPlayer
                        ).value

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
                                // Show preview gobblet if dragging tier is not null and can be stacked
                                val showPreview = draggingTier != null && isInBound && canBeStacked

                                if (showPreview) {
                                    // Add preview gobblet
                                    GobbletComponent(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .padding(16.dp)
                                            .fillMaxSize()
                                            .alpha(0.5f),
                                        tier = draggingTier!!,
                                        player = currentPlayer,
                                        colors = GobbletComponentDefaults.colors(
                                            tierColors = tierColors
                                        )
                                    )
                                } else if (item == null) {
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
                                        player = item.player,
                                        colors = GobbletComponentDefaults.colors(
                                            tierColors = tierColors
                                        )
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
    gridColor: Color,
    gridSize: Int = 3,
    strokeWidth: Dp = 3.dp
) = drawBehind {
    // Draw the horizontal lines
    for (i in 1 until gridSize) {
        val y = size.height * i / gridSize

        drawLine(
            color = gridColor,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
            strokeWidth = strokeWidth.toPx(),
        )
    }

    // Draw the vertical lines
    for (i in 1 until gridSize) {
        val x = size.width * i / gridSize

        drawLine(
            color = gridColor,
            start = Offset(x = x, y = 0f),
            end = Offset(x = x, y = size.height),
            strokeWidth = strokeWidth.toPx(),
        )
    }
}

private fun Modifier.drawWinnerLine(
    winner: GameResult.Winner,
    color: Color,
    gridSize: Int = 3,
) = drawWithContent {
    drawContent()

    val winnerLine = winner.line

    val squareHeight = size.height / gridSize
    val squareMiddleHeight = squareHeight / 2

    val squareWidth = size.width / gridSize
    val squareMiddleWidth = squareWidth / 2

    val startOffset = Offset(
        x = squareWidth * (winnerLine.first() % gridSize) + squareMiddleWidth,
        y = squareHeight * (winnerLine.first() / gridSize) + squareMiddleHeight
    )

    val endOffset = Offset(
        x = squareWidth * (winnerLine.last() % gridSize) + squareMiddleWidth,
        y = squareHeight * (winnerLine.last() / gridSize) + squareMiddleHeight
    )

    drawLine(
        color = color,
        start = startOffset,
        end = endOffset,
        strokeWidth = LINE_STROKE_WIDTH.toPx(),
        cap = StrokeCap.Round
    )
}

private val LINE_STROKE_WIDTH = 26.dp

object BoardDefaults {
    @Composable
    fun colors(
        tierColors: TierColors = TierColors.defaultTierColors(),
        gridColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        cantBeStackedSurfaceColor: Color = MaterialTheme.colorScheme.error,
        normalSurfaceColor: Color = MaterialTheme.colorScheme.surface,
    ): BoardColors = BoardColors(
        gridColor = gridColor,
        player1WinnerLineColor = tierColors.player1Color,
        player2WinnerLineColor = tierColors.player2Color,
        player1CanBeStackedSurfaceColor = tierColors.player1ContainerColor.copy(alpha = 0.28f),
        player2CanBeStackedSurfaceColor = tierColors.player2ContainerColor.copy(alpha = 0.28f),
        cantBeStackedSurfaceColor = cantBeStackedSurfaceColor,
        normalSurfaceColor = normalSurfaceColor,
    )
}

@Immutable
class BoardColors internal constructor(
    val gridColor: Color,
    val player1WinnerLineColor: Color,
    val player2WinnerLineColor: Color,
    val player1CanBeStackedSurfaceColor: Color,
    val player2CanBeStackedSurfaceColor: Color,
    val cantBeStackedSurfaceColor: Color,
    val normalSurfaceColor: Color,
) {
    @Composable
    fun winnerLineColor(
        player: Player
    ): State<Color> = rememberUpdatedState(
        newValue = when (player) {
            Player.PLAYER_1 -> player1WinnerLineColor
            Player.PLAYER_2 -> player2WinnerLineColor
        }
    )

    @Composable
    fun surfaceColor(
        isInBound: Boolean,
        canBeStacked: Boolean,
        player: Player
    ): State<Color> = rememberUpdatedState(
        newValue = when {
            isInBound && canBeStacked && player == Player.PLAYER_1 -> player1CanBeStackedSurfaceColor
            isInBound && canBeStacked && player == Player.PLAYER_2 -> player2CanBeStackedSurfaceColor
            isInBound && !canBeStacked -> cantBeStackedSurfaceColor
            else -> normalSurfaceColor
        }
    )
}
