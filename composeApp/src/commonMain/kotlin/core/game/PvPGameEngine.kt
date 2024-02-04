package core.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.GameType
import model.GobbletTier

class PvPGameEngine : GameEngine {
    override val gameType: GameType = GameType.PLAYER_VS_PLAYER

    private val _state = MutableStateFlow(GameEngine.State())
    override val state = _state.asStateFlow()

    override suspend fun start() {
        _state.emit(GameEngine.State())
    }

    override suspend fun reset() {
        _state.emit(GameEngine.State())
    }

    override suspend fun makeMove(index: Int, tier: GobbletTier) {
        _state.update { currentState ->
            // Check if the move is valid
            check(currentState.board.canInsertGobbletAt(index, tier)) {
                "Invalid move: $tier cannot be placed at $index"
            }

            // Remove the piece from the player's inventory, if it's their turn
            val player1Items = if (currentState.isPlayer1Turn) {
                currentState.player1Items - tier
            } else {
                currentState.player1Items
            }

            val player2Items = if (currentState.isPlayer2Turn) {
                currentState.player2Items - tier
            } else {
                currentState.player2Items
            }

            currentState.copy(
                board = currentState.board.insertGobbletAt(
                    index = index,
                    tier = tier,
                    player = currentState.currentPlayer
                ),
                currentPlayer = currentState.currentPlayer.next(),
                player1Items = player1Items,
                player2Items = player2Items,
                playedMoves = currentState.playedMoves + 1
            )
        }
    }
}
