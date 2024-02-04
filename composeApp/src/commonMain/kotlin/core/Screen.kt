package core

import model.GameType

sealed interface Screen {
    data object GameSelector : Screen

    data class Game(
        val gameType: GameType
    ) : Screen
}
