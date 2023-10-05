package presentation.game

import model.GobbletBoardItem

data class GameScreenUiState(
    val boardSize: Int = 3,
    val boardGobblets: List<GobbletBoardItem?> = emptyBoardList(boardSize),
)

internal fun emptyBoardList(boardSize: Int, ): List<GobbletBoardItem?> {
    return List(boardSize * boardSize) { null }
}
