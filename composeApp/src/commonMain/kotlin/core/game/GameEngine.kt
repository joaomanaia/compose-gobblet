package core.game

import core.utils.kotlin.times
import kotlinx.coroutines.flow.StateFlow
import model.*

interface GameEngine {
    /**
     * @param playedMoves The number of moves that have been played so far.
     */
    data class State(
        val board: GobbletBoard = GobbletBoard.empty(),
        val currentPlayer: Player = Player.PLAYER_1,
        val player1Items: List<GobbletTier> = defaultPlayerItems(),
        val player2Items: List<GobbletTier> = defaultPlayerItems(),
        val playedMoves: Int = 0
    ) {
        val isPlayer1Turn: Boolean
            get() = currentPlayer == Player.PLAYER_1

        val isPlayer2Turn: Boolean
            get() = currentPlayer == Player.PLAYER_2
    }

    val gameType: GameType

    val state: StateFlow<State>

    suspend fun start()

    suspend fun reset()

    suspend fun makeMove(index: Int, tier: GobbletTier)
}

internal fun defaultPlayerItems(boardSize: Int = 3): List<GobbletTier> {
    val items =  GobbletTier.allTiers * boardSize

    return items.sorted()
}
