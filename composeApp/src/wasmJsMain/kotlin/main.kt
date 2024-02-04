import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import presentation.App

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}