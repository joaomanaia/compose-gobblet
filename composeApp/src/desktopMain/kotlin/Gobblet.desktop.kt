import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import core.presentation.theme.GobbletTheme
import di.gameModule
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import presentation.game.GameScreen

@Composable
fun ApplicationScope.GobbletDesktop() {
    Window(onCloseRequest = ::exitApplication, title = "Gobblet") {
        KoinApplication(
            application = {
                modules(gameModule)
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
