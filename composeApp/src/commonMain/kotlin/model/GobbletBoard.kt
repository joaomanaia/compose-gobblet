package model

import kotlin.jvm.JvmInline
import kotlin.random.Random

@JvmInline
value class GobbletBoard internal constructor(
    val gobblets: List<GobbletBoardItem?>
) {
    init {
        require(gobblets.size == BOARD_SIZE) {
            "The size of the gobblets list must be $BOARD_SIZE"
        }
    }

    val size: Int
        get() = gobblets.size

    companion object {
        /**
         * The size of the board.
         */
        const val BOARD_SIZE = 9

        fun empty(): GobbletBoard {
            return GobbletBoard(
                gobblets = List(BOARD_SIZE) { null }
            )
        }

        /**
         * Creates a random board with random gobblets for testing purposes.
         */
        internal fun randomBoard(
            random: Random = Random.Default
        ): GobbletBoard {
            return GobbletBoard(
                gobblets = List(BOARD_SIZE) {
                    GobbletBoardItem(
                        tier = GobbletTier.allTiers.random(random),
                        player = Player.entries.random(random)
                    )
                }
            )
        }

        internal val board3x3WinPositions = listOf(
            // Horizontal
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            // Vertical
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            // Diagonal
            listOf(0, 4, 8),
            listOf(2, 4, 6),
        )
    }

    fun getGameResult(
        currentPlayerGobblets: List<GobbletTier>,
        playedMoves: Int
    ): GameResult? {
        // If the game has been played less than 5 times, then it's not over yet
        if (playedMoves < 5) return null

        for (winnerLine in board3x3WinPositions) {
            val gobblet = getGobbletAt(winnerLine.first())

            // If all the gobblets in the line are the same, then we have a winner
            if (gobblet != null && winnerLine.all { getGobbletAt(it)?.player == gobblet.player }) {
                return GameResult.Winner(
                    player = gobblet.player,
                    line = winnerLine,
                    gobblets = winnerLine.mapNotNull { getGobbletAt(it)?.tier }
                )
            }
        }

        // It's a tie if there are no more empty spaces on the board and no more remaining gobblets
        // to be stacked by the current player
        val currentPlayerMaxRemainingGobblet = currentPlayerGobblets.maxOrNull()

        val allSpacesFilled = gobblets.all { it != null }

        // If there are empty spaces on the board, then the game is not over yet
        if (!allSpacesFilled) {
            return null
        }

        // If there are no more empty spaces on the board and no more remaining gobblets to be
        // stacked by the current player, then it's a tie
        if (allSpacesFilled && currentPlayerMaxRemainingGobblet == null) {
            return GameResult.Tie
        }

        val canStackGobblets = gobblets.any { currentPlayerMaxRemainingGobblet?.canBeStackedOn(it?.tier) == true }

        // If the player can't stack any gobblet, then it's a tie
        if (!canStackGobblets) {
            return GameResult.Tie
        }

        return null
    }

    fun getGobbletAt(index: Int): GobbletBoardItem? {
        return gobblets[index]
    }

    fun canInsertGobbletAt(index: Int, tier: GobbletTier): Boolean {
        val gobblet = getGobbletAt(index)

        return gobblet == null || gobblet.tier < tier
    }

    fun insertGobbletAt(
        index: Int,
        tier: GobbletTier,
        player: Player
    ): GobbletBoard {
        // Update the board with the new gobblet
        val newBoardGobblets = gobblets.toMutableList()

        newBoardGobblets[index] = GobbletBoardItem(
            tier = tier,
            player = player
        )

        return GobbletBoard(
            gobblets = newBoardGobblets
        )
    }

    fun toBoardString(): String {
        val boardString = StringBuilder()

        for (i in 0 until size) {
            if (i % 3 == 0 && i != 0) {
                boardString.append("\n---+---+---\n")
            }

            val gobblet = getGobbletAt(i)
            if (gobblet != null) {
                val tier = gobblet.tier.tier
                val player = gobblet.player.ordinal + 1

                // When player 1 is represented by color blue and player 2 by color red
                if (player == 1) {
                    boardString.append(" \u001B[34m$tier\u001B[0m ")
                } else {
                    boardString.append(" \u001B[31m$tier\u001B[0m ")
                }
            } else {
                boardString.append("   ")
            }

            if (i % 3 != 2) {
                boardString.append("|")
            }
        }

        return boardString.toString()
    }
}
