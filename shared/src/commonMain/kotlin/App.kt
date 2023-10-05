import androidx.compose.runtime.Composable
import core.presentation.theme.GobbletTheme
import di.appModule
import org.koin.compose.KoinApplication
import presentation.game.GameScreen

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        GobbletTheme {
            GameScreen()
        }
    }
}

expect fun getPlatformName(): String