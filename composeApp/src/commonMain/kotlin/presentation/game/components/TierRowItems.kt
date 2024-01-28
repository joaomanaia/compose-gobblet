package presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TierRowItems(
    modifier: Modifier = Modifier,
    items: List<GobbletTier>,
    player: Player,
    enabled: Boolean = true,
) {
    val groupedItems by remember(items) {
        derivedStateOf {
            items.groupBy { it }
        }
    }

    val surfaceColor = if (enabled) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        tonalElevation = 8.dp,
        color = surfaceColor
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {
            if (items.isEmpty()) {
                Text(
                    text = "No More Pieces",
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
