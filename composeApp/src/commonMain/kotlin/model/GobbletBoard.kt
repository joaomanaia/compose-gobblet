package model

import kotlin.jvm.JvmInline
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

typealias Winner = Pair<Player, Line>

typealias Line = List<Int>

@JvmInline
value class GobbletBoard private constructor(
    val gobblets: List<GobbletBoardItem?>
) {
    val boardSize: Int
        get() = sqrt(gobblets.size.toDouble()).roundToInt()

    val winner: Winner?
        get() {
            for (winnerLine in board3x3WinPositions) {
                // If all the gobblets in the line are the same, then we have a winner
                val gobblet = getGobbletAt(winnerLine.first())

                if (gobblet != null && winnerLine.all { getGobbletAt(it)?.player == gobblet.player }) {
                    return gobblet.player to winnerLine
                }
            }

            return null
        }

    val isGameEnded: Boolean
        get() = winner != null

    fun getGobbletAt(index: Int): GobbletBoardItem? {
        return gobblets[index]
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

    companion object {
        fun empty(boardSize: Int = 3): GobbletBoard {
            return GobbletBoard(
                gobblets = List(boardSize * boardSize) { null }
            )
        }

        /**
         * Creates a random board with random gobblets for testing purposes.
         */
        internal fun randomBoard(
            boardSize: Int = 3,
            random: Random = Random.Default
        ): GobbletBoard {
            return GobbletBoard(
                gobblets = List(boardSize * boardSize) {
                    GobbletBoardItem(
                        tier = GobbletTier.allTiers.random(random),
                        player = Player.entries.random(random)
                    )
                }
            )
        }

        private val board3x3WinPositions = listOf(
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
}
