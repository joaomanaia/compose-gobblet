package di

import org.koin.dsl.module
import presentation.game.GameScreenViewModel

val appModule = module {
    single { GameScreenViewModel() }
}
