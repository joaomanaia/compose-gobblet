package presentation.previews.game.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.theme.GobbletTheme
import model.GobbletBoardItem
import model.GobbletTier
import model.Player
import presentation.game.components.GobbletBoard

@Preview
@Composable
private fun GobbletBoardPreview() {
    val randomBoardGobblets = List(9) {
        GobbletBoardItem(
            tier = GobbletTier.allTiers.random(),
            player = Player.entries.random(),
        )
    }

    GobbletTheme {
        Surface {
            GobbletBoard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                boardGobblets = randomBoardGobblets
            )
        }
    }
}