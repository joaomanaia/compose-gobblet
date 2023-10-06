package presentation.previews.game.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import core.presentation.theme.GobbletTheme
import model.GobbletBoardItem
import model.GobbletTier
import model.Player
import presentation.game.GameScreen
import presentation.game.GameScreenUiState

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GameScreenPreview() {
    GobbletTheme {
        Surface {
            GameScreen(
                uiState = GameScreenUiState(
                    boardGobblets = List(9) {
                        GobbletBoardItem(
                            tier = GobbletTier.allTiers.random(),
                            player = Player.entries.random()
                        )
                    }
                ),
                onEvent = {}
            )
        }
    }
}