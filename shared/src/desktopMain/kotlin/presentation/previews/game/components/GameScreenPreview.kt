package presentation.previews.game.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import core.presentation.theme.GobbletTheme
import model.GobbletBoard
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
                    board = GobbletBoard.randomBoard()
                ),
                onEvent = {}
            )
        }
    }
}