package model

import kotlin.test.*

/**
 * Tests for [GobbletBoard].
 */
internal class GobbletBoardTest {
    @Test
    fun test_getGobbletAt() {
        val gobblet = GobbletBoardItem(
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )

        val board = GobbletBoard(
            gobblets = listOf(
                gobblet,
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

        assertSame(
            expected = gobblet,
            actual = board.getGobbletAt(0)
        )
    }

    @Test
    fun canInsertGobbletAt_whenInsertingInSamePosition() {
        val gobblet = GobbletBoardItem(
            tier = GobbletTier.TIER_2,
            player = Player.PLAYER_1
        )

        val board = GobbletBoard(
            gobblets = listOf(
                gobblet,
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

        // Can't insert a gobblet at the same position with the same or lower tier
        assertFalse(board.canInsertGobbletAt(0, GobbletTier.TIER_1))
        assertFalse(board.canInsertGobbletAt(0, GobbletTier.TIER_2))

        // Can insert a gobblet at the same position with a higher tier
        assertTrue(board.canInsertGobbletAt(0, GobbletTier.TIER_3))

        // Check if you can insert gobblet at other positions
        for (i in 1 until GobbletBoard.BOARD_SIZE) {
            assertTrue(board.canInsertGobbletAt(i, GobbletTier.TIER_1))
            assertTrue(board.canInsertGobbletAt(i, GobbletTier.TIER_2))
            assertTrue(board.canInsertGobbletAt(i, GobbletTier.TIER_3))
        }
    }

    @Test
    fun test_insertGobbletAt() {
        val gobblet = GobbletBoardItem(
            tier = GobbletTier.TIER_1,
            player = Player.PLAYER_1
        )

        val board = GobbletBoard.empty()

        // First check if the gobblet can be inserted
        assertTrue(board.canInsertGobbletAt(0, GobbletTier.TIER_1))

        val newBoard = board.insertGobbletAt(
            index = 0,
            tier = gobblet.tier,
            player = gobblet.player
        )

        assertEquals(
            expected = gobblet,
            actual = newBoard.getGobbletAt(0)
        )

        // Check if only the gobblet at the specified index was changed
        for (i in 1 until GobbletBoard.BOARD_SIZE) {
            assertNull(newBoard.getGobbletAt(i))
        }
    }

    @Test
    fun test_gameResult_whenPlayerWins() {
        // Test all possible winning positions
        for (winningPosition in GobbletBoard.board3x3WinPositions) {
            val board = GobbletBoard(
                // Create a board with gobblets at the winning position
                gobblets = List(GobbletBoard.BOARD_SIZE) {
                    if (it in winningPosition) {
                        GobbletBoardItem(
                            tier = GobbletTier.TIER_1,
                            player = Player.PLAYER_1
                        )
                    } else {
                        null
                    }
                }
            )

            val expectedGameResult = GameResult.Winner(
                player = Player.PLAYER_1,
                line = winningPosition,
                gobblets = winningPosition.map { GobbletTier.TIER_1 }
            )

            assertEquals(
                expected = expectedGameResult,
                actual = board.getGameResult(
                    // No need to check the other player's gobblets since the board is already
                    // filled with the winning player's gobblets
                    currentPlayerGobblets = emptyList(),
                    playedMoves = GobbletBoard.BOARD_SIZE
                )
            )
        }
    }

    @Test
    fun test_gameResult_whenTieAndNoMoreRemainingGobblets() {
        val board = GobbletBoard(
            // Create a board with gobblets at tie positions
            gobblets = listOf(
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
            )
        )

        assertEquals(
            expected = GameResult.Tie,
            actual = board.getGameResult(
                currentPlayerGobblets = emptyList(),
                playedMoves = GobbletBoard.BOARD_SIZE
            )
        )
    }

    @Test
    fun test_gameResult_whenTieWithRemainingGobbets() {
        val board = GobbletBoard(
            // Create a board with gobblets at tie positions
            gobblets = listOf(
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
            )
        )

        // Since there are remaining gobblets but the gobblets can't be stacked on the board, it's a tie
        assertEquals(
            expected = GameResult.Tie,
            actual = board.getGameResult(
                currentPlayerGobblets = listOf(GobbletTier.TIER_1),
                playedMoves = GobbletBoard.BOARD_SIZE
            )
        )

        // Since there are remaining gobblets and the gobblets can be stacked on the board and
        // the current player can place more gobblets, it's not a tie
        assertNull(
            board.getGameResult(
                currentPlayerGobblets = listOf(GobbletTier.TIER_1, GobbletTier.TIER_2),
                playedMoves = GobbletBoard.BOARD_SIZE
            )
        )
    }

    @Test
    fun test_gameResult_whenNoWinnerAndNoTie() {
        val board = GobbletBoard(
            // Create a board with no winner and no tie
            gobblets = listOf(
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_2),
                GobbletBoardItem(tier = GobbletTier.TIER_1, player = Player.PLAYER_1),
                null
            )
        )

        assertNull(
            board.getGameResult(
                currentPlayerGobblets = emptyList(),
                playedMoves = GobbletBoard.BOARD_SIZE - 1
            )
        )
    }
}
