package presentation.game

import core.utils.kotlin.times
import model.GobbletBoard
import model.GobbletTier
import model.Player

data class GameScreenUiState(
    val board: GobbletBoard = GobbletBoard.empty(),
    val currentPlayer: Player = Player.PLAYER_1,
    val player1Items: List<GobbletTier> = defaultPlayerItems(),
    val player2Items: List<GobbletTier> = defaultPlayerItems(),
) {
    val isPlayer1Turn: Boolean
        get() = currentPlayer == Player.PLAYER_1

    val isPlayer2Turn: Boolean
        get() = currentPlayer == Player.PLAYER_2
}

internal fun defaultPlayerItems(boardSize: Int = 3): List<GobbletTier> {
    val allItems = GobbletTier.allTiers * boardSize

    return allItems.sorted()
}
