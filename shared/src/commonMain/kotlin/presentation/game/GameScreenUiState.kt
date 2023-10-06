package presentation.game

import core.utils.kotlin.times
import model.GobbletBoardItem
import model.GobbletTier
import model.Player

data class GameScreenUiState(
    val boardSize: Int = 3,
    val boardGobblets: List<GobbletBoardItem?> = emptyBoardList(boardSize),
    val currentPlayer: Player = Player.PLAYER_1,
    val player1Items: List<GobbletTier> = defaultPlayerItems(boardSize),
    val player2Items: List<GobbletTier> = defaultPlayerItems(boardSize),
)

internal fun emptyBoardList(boardSize: Int): List<GobbletBoardItem?> {
    return List(boardSize * boardSize) { null }
}

internal fun defaultPlayerItems(boardSize: Int): List<GobbletTier> {
    val allItems = GobbletTier.allTiers * boardSize

    return allItems.sorted()
}
