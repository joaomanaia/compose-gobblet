package presentation.game

import core.utils.kotlin.times
import model.GobbletBoardItem
import model.GobbletTier
import model.Player

data class GameScreenUiState(
    val boardSize: Int = 3,
    val boardGobblets: List<GobbletBoardItem?> = emptyBoardList(boardSize),
    val playerTurn: Player = Player.PLAYER_1,
    val player1Items: List<GobbletTier> = defaultPlayerItems(boardSize),
    val player2Items: List<GobbletTier> = defaultPlayerItems(boardSize),
)

internal fun emptyBoardList(boardSize: Int): List<GobbletBoardItem?> {
    return List(boardSize * boardSize) { null }
}

internal fun defaultPlayerItems(boardSize: Int): List<GobbletTier> {
    val allItems = GobbletTier.entries * boardSize

    return allItems.sorted()
}
