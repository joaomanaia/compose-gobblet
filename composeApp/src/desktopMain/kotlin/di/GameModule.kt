package di

import org.koin.dsl.module
import presentation.game.GameScreenViewModel

val gameModule = module {
    single { GameScreenViewModel() }
}
