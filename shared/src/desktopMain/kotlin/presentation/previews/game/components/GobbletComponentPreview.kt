package presentation.previews.game.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.theme.GobbletTheme
import model.GobbletTier
import model.Player
import presentation.game.components.GobbletComponent

@Preview
@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun GobbletComponentPreview() {
    GobbletTheme {
        Surface {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                GobbletTier.allTiers.forEach { tier ->
                    Player.entries.forEach { player ->
                        GobbletComponent(
                            tier = tier,
                            player = player
                        )
                    }
                }
            }
        }
    }
}
