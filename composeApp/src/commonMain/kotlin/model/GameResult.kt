package model

typealias Line = List<Int>

/**
 * A sealed class that represents the result of a game.
 *
 * A game can either be won by a player, or it can be a tie.
 */
sealed interface GameResult {
    /**
     * A data class that represents a [player] winning the game by placing a gobblet on a [line].
     */
    data class Winner(
        val player: Player,
        val line: Line,
        val gobblets: List<GobbletTier>,
    ) : GameResult {
        init {
            require(line.size == 3) { "A line must have 3 elements" }
            require(gobblets.size == 3) { "A line must have 3 gobblets" }
        }
    }

    /**
     * An object that represents a tie.
     */
    data object Tie : GameResult

    /**
     * @return true if the game was won by [player], false otherwise.
     */
    fun wasWonBy(player: Player): Boolean {
        return when (this) {
            is Winner -> this.player == player
            else -> false
        }
    }
}
