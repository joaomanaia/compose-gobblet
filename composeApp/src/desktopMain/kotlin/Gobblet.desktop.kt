import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import presentation.App

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ApplicationScope.GobbletDesktop() {
    Window(onCloseRequest = ::exitApplication, title = "Gobblet") {
        App()
    }
}
