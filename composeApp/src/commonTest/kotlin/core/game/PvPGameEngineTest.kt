package core.game

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import model.GobbletBoard
import model.GobbletBoardItem
import model.GobbletTier
import model.Player
import kotlin.test.*

/**
 * Tests for [PvPGameEngine].
 */
internal class PvPGameEngineTest {
    private lateinit var engine: PvPGameEngine

    @BeforeTest
    fun setup() {
        engine = PvPGameEngine()
    }

    @Test
    fun testStart() = runTest {
        engine.start()

        // Check if the game state is initialized
        val state = engine.state.first()
        assertEquals(
            expected = GameEngine.State(),
            actual = state
        )
    }

    @Test
    fun testReset() = runTest {
        engine.start()
        // Make a move to change the game state
        engine.makeMove(0, GobbletTier.TIER_1)

        engine.reset()

        // Check if the game state is redefined
        val state = engine.state.first()
        assertEquals(
            expected = GameEngine.State(),
            actual = state
        )
    }

    @Test
    fun testMakeMove_whenMoveIsValid() = runTest {
        engine.start()

        engine.makeMove(0, GobbletTier.TIER_1)

        // Check if the game state is updated
        val state = engine.state.first()

        assertThat(state).isEqualTo(
            GameEngine.State(
                board = GobbletBoard(
                    gobblets = listOf(
                        GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                ),
                currentPlayer = Player.PLAYER_2,
                player1Items = defaultPlayerItems() - GobbletTier.TIER_1,
                player2Items = defaultPlayerItems(),
                playedMoves = 1
            )
        )

        // Make another move
        engine.makeMove(1, GobbletTier.TIER_2)

        // Check if the game state is updated
        val state2 = engine.state.first()

        assertThat(state2).isEqualTo(
            GameEngine.State(
                board = GobbletBoard(
                    gobblets = listOf(
                        GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                        GobbletBoardItem(tier = GobbletTier.TIER_2, player = Player.PLAYER_2),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                ),
                currentPlayer = Player.PLAYER_1,
                player1Items = defaultPlayerItems() - GobbletTier.TIER_1,
                player2Items = defaultPlayerItems() - GobbletTier.TIER_2,
                playedMoves = 2
            )
        )
    }

    @Test
    fun testMakeMove_whenMoveIsInvalid() = runTest {
        engine.start()

        // Make a move
        engine.makeMove(0, GobbletTier.TIER_1)

        // Check if the game state is unchanged
        val state = engine.state.first()

        assertThat(state).isEqualTo(
            GameEngine.State(
                board = GobbletBoard(
                    gobblets = listOf(
                        GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                ),
                currentPlayer = Player.PLAYER_2,
                player1Items = defaultPlayerItems() - GobbletTier.TIER_1,
                player2Items = defaultPlayerItems(),
                playedMoves = 1
            )
        )

        // Try to make the same move again
        assertFails {
            engine.makeMove(0, GobbletTier.TIER_1)
        }
    }
}