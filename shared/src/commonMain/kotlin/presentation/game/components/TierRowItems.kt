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
    player: Player
) {
    val groupedItems by remember {
        derivedStateOf {
            items.groupBy { it }
        }
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        tonalElevation = 4.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {
            groupedItems.forEach { item ->
                BadgedBox(
                    modifier = Modifier,
                    badge = {
                        Badge {
                            Text(text = item.value.size.toString())
                        }
                    }
                ) {
                    DragTarget(
                        dataToDrop = item.key,
                    ) {
                        GobbletComponent(
                            tier = item.key,
                            player = player
                        )
                    }
                }
            }
        }
    }
}
