import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import gobblet.composeapp.generated.resources.Res
import gobblet.composeapp.generated.resources.ic_launcher_round
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.App

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ApplicationScope.GobbletDesktop() {
    val appIcon = painterResource(Res.drawable.ic_launcher_round)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Gobblet",
        icon = appIcon
    ) {
        App()
    }
}
