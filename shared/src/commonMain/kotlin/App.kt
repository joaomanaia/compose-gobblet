import androidx.compose.runtime.Composable
import core.presentation.theme.TicTacToe2Theme
import presentation.game.GameScreen

@Composable
fun App() {
    TicTacToe2Theme {
        GameScreen()
    }
}

expect fun getPlatformName(): String