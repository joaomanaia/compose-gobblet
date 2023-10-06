package model

import assertk.assertThat
import assertk.assertions.*
import kotlin.test.Test

/**
 * Tests for [GobbletBoard].
 */
internal class GobbletBoardTest {
    @Test
    fun `GobbletBoard#empty() returns an empty board`() {
        val boardSize = 3
        val board = GobbletBoard.empty(boardSize)

        assertThat(board.gobblets).hasSize(boardSize * boardSize)
        assertThat(board.gobblets.all { it == null }).isTrue()
    }

    @Test
    fun `GobbletBoard#insertGobbletAt() inserts the gobblet at the given index`() {
        val boardSize = 3
        val board = GobbletBoard.empty(boardSize)
        val index = 4
        val gobblet = GobbletBoardItem(
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )
        val newBoard = board.insertGobbletAt(
            index = index,
            tier = gobblet.tier,
            player = gobblet.player
        )

        assertThat(newBoard.getGobbletAt(index)).isEqualTo(gobblet)
    }

    @Test
    fun `boardSize returns the size of the board`() {
        val boardSize = 3
        val board = GobbletBoard.empty(boardSize)

        assertThat(board.boardSize).isEqualTo(boardSize)
    }

    @Test
    fun `winner returns the winner if there is one`() {
        val boardSize = 3
        var board = GobbletBoard.empty(boardSize)

        assertThat(board.winner).isEqualTo(null)

        // Fill the first row with player 1's gobblets
        for (i in 0 until boardSize) {
            board = board.insertGobbletAt(
                index = i,
                tier = GobbletTier.TIER_1,
                player = Player.PLAYER_1
            )
        }

        assertThat(board.winner).isNotNull()
        assertThat(board.winner).isEqualTo(Player.PLAYER_1)
    }

    @Test
    fun `winner returns null if there is no winner`() {
        val boardSize = 3
        var board = GobbletBoard.empty(boardSize)

        assertThat(board.winner).isNull()

        // Add a gobblet (player 1) to the first and last item in the first row
        board = board.insertGobbletAt(
            index = 0,
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )
        board = board.insertGobbletAt(
            index = 2,
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )

        // Add a gobblet (player 2) to the middle item in the first row
        board = board.insertGobbletAt(
            index = 1,
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_2
        )

        assertThat(board.winner).isNull()
    }

    @Test
    fun `winner returns the winner if are different tiers`() {
        val boardSize = 3
        var board = GobbletBoard.empty(boardSize)

        assertThat(board.winner).isNull()

        // Add a tier 1 gobblet to the first and last item in the first row
        board = board.insertGobbletAt(
            index = 0,
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )

        // Add a tier 2 gobblet to the middle item in the first row
        board = board.insertGobbletAt(
            index = 1,
            tier = GobbletTier.TIER_2,
            player = Player.PLAYER_1
        )

        // Add a tier 3 gobblet to the last item in the first row
        board = board.insertGobbletAt(
            index = 2,
            tier = GobbletTier.TIER_3,
            player = Player.PLAYER_1
        )

        assertThat(board.winner).isEqualTo(Player.PLAYER_1)
    }
}
