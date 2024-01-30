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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.theme.spacing
import model.GobbletTier
import model.Player
import core.DragTarget
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
    onPlayAgainClick: () -> Unit = {}
) {
    val groupedItems by remember(items) {
        derivedStateOf {
            items.groupBy { it }
        }
    }

    val surfaceColor = when {
        winner != null && winner.first == player -> {
            if (player == Player.PLAYER_1) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            }
        }

        enabled -> {
            MaterialTheme.colorScheme.surface
        }

        else -> {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
        }
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        tonalElevation = 8.dp,
        color = surfaceColor
    ) {
        TierItemsContainer(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            rowLayout = rowLayout
        ) {
            if (winner != null && winner.first == player) {
                Text(
                    text = "Winner",
                    style = MaterialTheme.typography.headlineMedium
                )

                Button(
                    onClick = onPlayAgainClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (player == Player.PLAYER_1) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.tertiary
                        }
                    ),
                ) {
                    Text(text = "Play Again")
                }
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
                                enabled = enabled
                            )
                        }
                    }
                }
            }
        }
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
