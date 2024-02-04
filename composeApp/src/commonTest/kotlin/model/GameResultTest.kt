package model

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for [GameResult].
 */
internal class GameResultTest {
    @Test
    fun test_wasWonBy_whenResultIsTie() {
        val result = GameResult.Tie

        assertFalse(result.wasWonBy(Player.PLAYER_1))
        assertFalse(result.wasWonBy(Player.PLAYER_2))
    }

    @Test
    fun test_wasWonBy_whenResultIsWonByPlayers() {
        // For player 1
        val result1 = GameResult.Winner(
            player = Player.PLAYER_1,
            line = listOf(1, 2, 3),
            gobblets = listOf(
                GobbletTier.TIER_1,
                GobbletTier.TIER_2,
                GobbletTier.TIER_3
            )
        )

        assertTrue(result1.wasWonBy(Player.PLAYER_1))
        assertFalse(result1.wasWonBy(Player.PLAYER_2))

        // For player 2
        val result2 = GameResult.Winner(
            player = Player.PLAYER_2,
            line = listOf(1, 2, 3),
            gobblets = listOf(
                GobbletTier.TIER_1,
                GobbletTier.TIER_2,
                GobbletTier.TIER_3
            )
        )

        assertFalse(result2.wasWonBy(Player.PLAYER_1))
        assertTrue(result2.wasWonBy(Player.PLAYER_2))
    }
}