package presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import core.Screen
import core.presentation.theme.GobbletTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import presentation.game.GameScreen
import presentation.selector.GameSelectorScreen

@Composable
@ExperimentalMaterial3Api
fun App() {
    GobbletTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            KoinContext {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.GameSelector) }

                when (val screen = currentScreen) {
                    Screen.GameSelector -> {
                        GameSelectorScreen(
                            navigateToGame = { gameType ->
                                currentScreen = Screen.Game(
                                    gameType = gameType
                                )
                            }
                        )
                    }

                    is Screen.Game -> {
                        GameScreen(
                            viewModel = koinInject {
                                parametersOf(screen.gameType)
                            },
                            onBackClick = { currentScreen = Screen.GameSelector }
                        )
                    }
                }
            }
        }
    }
}
