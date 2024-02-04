package presentation.game

import core.game.GameEngine
import model.GameType

data class GameScreenUiState(
    val loading: Boolean = true,
    val gameState: GameEngine.State = GameEngine.State(),
    val gameType: GameType = GameType.PLAYER_VS_PLAYER
)
