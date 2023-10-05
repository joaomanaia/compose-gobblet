package presentation.previews.game.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.theme.GobbletTheme
import model.GobbletTier
import model.Player
import presentation.game.components.TierRowItems

@Preview
@Composable
private fun TierRowItemsPreview() {
    GobbletTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TierRowItems(
                    items = GobbletTier.entries,
                    player = Player.PLAYER_1
                )
                Spacer(modifier = Modifier.height(16.dp))
                TierRowItems(
                    items = GobbletTier.entries,
                    player = Player.PLAYER_2
                )
            }
        }
    }
}