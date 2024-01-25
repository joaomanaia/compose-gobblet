import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import core.presentation.theme.GobbletTheme
import di.gameModule
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import presentation.game.GameScreen
import presentation.game.GameScreenViewModel
import utils.ioDispatcher

@Composable
@ExperimentalComposeUiApi
fun GobbletWasmJs() {
    val ioScope: CoroutineScope = rememberCoroutineScope { ioDispatcher }

    val coroutineScopeModule = module {
        single { ioScope }
    }

    val koinApp = koinApplication {
        modules(coroutineScopeModule, gameModule)
    }

    val gameScreenViewModel: GameScreenViewModel by koinApp.koin.inject()

    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
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
}
