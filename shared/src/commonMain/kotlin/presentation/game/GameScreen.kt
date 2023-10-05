package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import presentation.game.components.GobbletBoard

@Composable
internal fun GameScreen() {
    val viewModel = getViewModel(
        key = "GameScreen",
        factory = viewModelFactory {
            GameScreenViewModel()
        }
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Column {
            Button(
                onClick = {
                    viewModel.onEvent(GameScreenUiEvent.OnRegenerateClick)
                },
            ) {
                Text(text = "Regenerate")
            }
            Spacer(modifier = Modifier.height(16.dp))
            GobbletBoard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                boardGobblets = uiState.boardGobblets,
                onItemClick = { index ->
                    viewModel.onEvent(GameScreenUiEvent.OnItemClick(index))
                }
            )
        }
    }
}
