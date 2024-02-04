package di

import core.game.GameEngine
import core.game.PvCGameEngine
import core.game.PvPGameEngine
import model.GameType
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import presentation.game.GameScreenViewModel

val gameModule = module {
    factory<GameEngine> { params ->
        when (params.get<GameType>()) {
            GameType.PLAYER_VS_PLAYER -> PvPGameEngine()
            GameType.PLAYER_VS_COMPUTER -> PvCGameEngine()
        }
    }

    factory { params ->
        GameScreenViewModel(
            gameEngine = get { parametersOf(params.get<GameType>()) }
        )
    }
}
