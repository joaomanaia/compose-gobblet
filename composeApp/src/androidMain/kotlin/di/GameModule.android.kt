package di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.game.GameScreenViewModel

actual val gameModule = module {
    viewModel { GameScreenViewModel() }
}
