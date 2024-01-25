import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import core.presentation.theme.GobbletTheme
import di.gameModule
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.module
import presentation.game.GameScreen
import utils.ioDispatcher

@Composable
fun ApplicationScope.GobbletDesktop() {
    val ioScope: CoroutineScope = rememberCoroutineScope { ioDispatcher }

    val coroutineScopeModule = module {
        single { ioScope }
    }

    Window(onCloseRequest = ::exitApplication, title = "Gobblet") {
        KoinApplication(
            application = {
                modules(coroutineScopeModule, gameModule)
            }
        ) {
            GobbletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(
                        viewModel = koinInject()
                    )
                }
            }
        }
    }
}
