import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import di.KoinStarter
import presentation.App

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun main() {
    KoinStarter.init()

    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}
