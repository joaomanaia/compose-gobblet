package core.game

import assertk.assertThat
import assertk.assertions.isEqualTo
import model.GobbletBoard
import model.GobbletBoardItem
import model.GobbletTier
import model.Player
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.measureTimedValue

/**
 * Tests for [PvCGameEngine]
 */
internal class ComputerGameEngineTest {
    private lateinit var computerGameEngine: PvCGameEngine

    @BeforeTest
    fun setUp() {
        computerGameEngine = PvCGameEngine()
    }

    @Test
    fun test_evaluateBoard() {
        val board = GobbletBoard.empty()

        val (result, duration) = measureTimedValue {
            computerGameEngine.evaluateBoard(
                gobblets = board.gobblets,
                gameResult = null
            )
        }

        assertThat(result).isEqualTo(0)
        println("Duration: $duration")
    }

    @Test
    fun test_getBestMove() {
        val board = GobbletBoard(
            gobblets = listOf(
                GobbletBoardItem(GobbletTier.TIER_3, Player.PLAYER_1),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )

        val currentPlayerGobblets = listOf(
            GobbletTier.TIER_1,
            GobbletTier.TIER_1,
            GobbletTier.TIER_1,
            GobbletTier.TIER_2,
            GobbletTier.TIER_2,
            GobbletTier.TIER_2,
            GobbletTier.TIER_3,
            GobbletTier.TIER_3,
            GobbletTier.TIER_3,
        )

        val (result, duration) = measureTimedValue {
            computerGameEngine.getBestMove(
                board = board,
                currentPlayerGobblets = currentPlayerGobblets,
                depth = 3,
                playedMoves = 1
            )
        }

        println("Best move -> Position: ${result?.first}, Tier: ${result?.second?.tier}")
        println("Duration: $duration")
    }
}
