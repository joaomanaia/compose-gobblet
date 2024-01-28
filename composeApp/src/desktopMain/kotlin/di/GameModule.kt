package di

import org.koin.dsl.module
import presentation.game.GameScreenViewModel

actual val gameModule = module {
    single { GameScreenViewModel() }
}
