import androidx.compose.ui.window.application
import di.KoinStarter
import org.koin.logger.SLF4JLogger

fun main() = application {
    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")

    KoinStarter.init {
        logger(SLF4JLogger())
    }

    GobbletDesktop()
}
