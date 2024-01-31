package presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import core.presentation.theme.spacing
import model.GobbletTier
import model.Player
import core.DragTarget
import core.presentation.theme.TierColors
import model.Winner

@Composable
@ExperimentalMaterial3Api
internal fun TierRowItems(
    modifier: Modifier = Modifier,
    items: List<GobbletTier>,
    player: Player,
    rowLayout: Boolean,
    winner: Winner? = null,
    enabled: Boolean = true,
    tierColors: TierColors = TierColors.defaultTierColors(),
    colors: TierRowItemsColors = TierRowItemsDefaults.colors(
        tierColors = tierColors
    ),
    shape: Shape = TierRowItemsDefaults.shape,
    onPlayAgainClick: () -> Unit = {}
) {
    val groupedItems by remember(items) {
        derivedStateOf {
            items.groupBy { it }
        }
    }

    val surfaceColor = colors.surfaceColor(
        winner = winner,
        player = player,
        enabled = enabled
    ).value

    val playAgainButtonColor = colors.playAgainButtonColor(player = player).value

    Surface(
        shape = shape,
        modifier = modifier,
        tonalElevation = 8.dp,
        color = surfaceColor
    ) {
        TierItemsContainer(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            rowLayout = rowLayout
        ) {
            if (winner != null && winner.first == player) {
                WinnerContent(
                    playAgainButtonColor = playAgainButtonColor,
                    onPlayAgainClick = onPlayAgainClick
                )
            } else {
                if (items.isEmpty()) {
                    Text(
                        text = "No Pieces",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                groupedItems.forEach { item ->
                    BadgedBox(
                        modifier = Modifier,
                        badge = {
                            Badge(
                                containerColor = BadgeDefaults.containerColor.copy(
                                    alpha = if (enabled) 1f else 0.1f
                                )
                            ) {
                                Text(
                                    text = item.value.size.toString(),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    ) {
                        DragTarget(
                            dataToDrop = item.key,
                            enabled = enabled
                        ) {
                            GobbletComponent(
                                tier = item.key,
                                player = player,
                                enabled = enabled,
                                colors = GobbletComponentDefaults.colors(tierColors = tierColors)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WinnerContent(
    playAgainButtonColor: Color,
    onPlayAgainClick: () -> Unit
) {
    Text(
        text = "Winner",
        style = MaterialTheme.typography.headlineMedium
    )

    Button(
        onClick = onPlayAgainClick,
        colors = ButtonDefaults.buttonColors(containerColor = playAgainButtonColor),
    ) {
        Text(text = "Play Again")
    }
}

@Composable
private fun TierItemsContainer(
    modifier: Modifier = Modifier,
    rowLayout: Boolean,
    content: @Composable () -> Unit
) {
    if (rowLayout) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacing.large,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
        ) {
            content()
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacing.large,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = modifier
        ) {
            content()
        }
    }
}

object TierRowItemsDefaults {
    val shape: Shape
        @Composable
        get() = MaterialTheme.shapes.medium

    @Composable
    fun colors(
        tierColors: TierColors = TierColors.defaultTierColors(),
        disabledSurfaceColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f)
    ): TierRowItemsColors = TierRowItemsColors(
        player1SurfaceColor = tierColors.player1ContainerColor.copy(alpha = 0.28f),
        player2SurfaceColor = tierColors.player2ContainerColor.copy(alpha = 0.28f),
        player1WinnerSurfaceColor = tierColors.player1ContainerColor,
        player2WinnerSurfaceColor = tierColors.player2ContainerColor,
        player1PlayAgainButtonColor = tierColors.player1Color,
        player2PlayAgainButtonColor = tierColors.player2Color,
        disabledSurfaceColor = disabledSurfaceColor
    )
}

@Immutable
class TierRowItemsColors internal constructor(
    val player1SurfaceColor: Color,
    val player2SurfaceColor: Color,
    val player1WinnerSurfaceColor: Color,
    val player2WinnerSurfaceColor: Color,
    val player1PlayAgainButtonColor: Color,
    val player2PlayAgainButtonColor: Color,
    val disabledSurfaceColor: Color,
) {
    @Composable
    fun surfaceColor(
        winner: Winner?,
        player: Player,
        enabled: Boolean
    ): State<Color> = rememberUpdatedState(
        newValue = when {
            winner != null && winner.first == Player.PLAYER_1 -> player1WinnerSurfaceColor
            winner != null && winner.first == Player.PLAYER_2 -> player2WinnerSurfaceColor
            enabled && player == Player.PLAYER_1 -> player1SurfaceColor
            enabled && player == Player.PLAYER_2 -> player2SurfaceColor
            else -> disabledSurfaceColor
        }
    )

    @Composable
    fun playAgainButtonColor(
        player: Player
    ): State<Color> = rememberUpdatedState(
        newValue = when (player) {
            Player.PLAYER_1 -> player1PlayAgainButtonColor
            Player.PLAYER_2 -> player2PlayAgainButtonColor
        }
    )
}
