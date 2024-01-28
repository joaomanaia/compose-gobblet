import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import core.presentation.theme.GobbletTheme
import di.gameModule
import org.koin.dsl.koinApplication
import presentation.game.GameScreen
import presentation.game.GameScreenViewModel

@Composable
@ExperimentalComposeUiApi
fun GobbletWasmJs() {
    val koinApp = koinApplication {
        modules(gameModule)
    }

    val gameScreenViewModel: GameScreenViewModel by koinApp.koin.inject()

    GobbletTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GameScreen(
                viewModel = gameScreenViewModel
            )
        }
    }
}
