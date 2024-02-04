package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for [GobbletTier].
 */
internal class GobbletTierTest {
    @Test
    fun test_canBeStackedOn() {
        val allTiers = GobbletTier.allTiers

        // A tier can be stacked on an empty space
        for (tier in allTiers) {
            assertTrue(tier canBeStackedOn null)
        }

        // A tier can be stacked on a tier with a lower tier
        for (tier in allTiers) {
            for (otherTier in allTiers) {
                assertEquals(
                    expected = tier.tier > otherTier.tier,
                    actual = tier canBeStackedOn otherTier
                )
            }
        }
    }
}
