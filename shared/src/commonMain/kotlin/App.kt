import androidx.compose.runtime.Composable
import core.presentation.theme.GobbletTheme
import presentation.game.GameScreen

@Composable
fun App() {
    GobbletTheme {
        GameScreen()
    }
}

expect fun getPlatformName(): String