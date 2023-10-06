package model

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import core.icons.GobbletTierIcons
import core.icons.gobblettiericons.Tier1
import core.icons.gobblettiericons.Tier2
import core.icons.gobblettiericons.Tier3
import kotlin.test.Test

/**
 * Tests for [GobbletTier].
 */
internal class GobbletTierTest {
    @Test
    fun `all items should have different tiers`() {
        assertThat(GobbletTier.TIER_1.tier).isEqualTo(1)
        assertThat(GobbletTier.TIER_2.tier).isEqualTo(2)
        assertThat(GobbletTier.TIER_3.tier).isEqualTo(3)
    }

    @Test
    fun `allTiers contains all tiers`() {
        assertThat(GobbletTier.allTiers).containsExactly(
            GobbletTier.TIER_1,
            GobbletTier.TIER_2,
            GobbletTier.TIER_3
        )
    }

    @Test
    fun `icon returns correct icon`() {
        assertThat(GobbletTier.TIER_1.icon).isEqualTo(GobbletTierIcons.Tier1)
        assertThat(GobbletTier.TIER_2.icon).isEqualTo(GobbletTierIcons.Tier2)
        assertThat(GobbletTier.TIER_3.icon).isEqualTo(GobbletTierIcons.Tier3)
    }

    @Test
    fun `test compare tiers`() {
        assertThat(GobbletTier.TIER_1 < GobbletTier.TIER_2).isEqualTo(true)
        assertThat(GobbletTier.TIER_1 < GobbletTier.TIER_3).isEqualTo(true)
        assertThat(GobbletTier.TIER_2 < GobbletTier.TIER_3).isEqualTo(true)

        // Test sorting
        val allItemsShuffled = GobbletTier.allTiers.shuffled()
        assertThat(allItemsShuffled.sorted()).containsExactly(
            GobbletTier.TIER_1,
            GobbletTier.TIER_2,
            GobbletTier.TIER_3
        )
    }

    @Test
    fun `test canBeStackedOn`() {
        // Tier 1 gobblets cannot be stacked on any other gobblet
        assertThat(GobbletTier.TIER_1 canBeStackedOn GobbletTier.TIER_1).isEqualTo(false)
        assertThat(GobbletTier.TIER_1 canBeStackedOn GobbletTier.TIER_2).isEqualTo(false)
        assertThat(GobbletTier.TIER_1 canBeStackedOn GobbletTier.TIER_3).isEqualTo(false)

        // Tier 2 gobblets can only be stacked on tier 1 gobblets
        assertThat(GobbletTier.TIER_2 canBeStackedOn GobbletTier.TIER_1).isEqualTo(true)
        assertThat(GobbletTier.TIER_2 canBeStackedOn GobbletTier.TIER_2).isEqualTo(false)
        assertThat(GobbletTier.TIER_2 canBeStackedOn GobbletTier.TIER_3).isEqualTo(false)

        // Tier 3 gobblets can be stacked on tier 1 and tier 2 gobblets
        assertThat(GobbletTier.TIER_3 canBeStackedOn GobbletTier.TIER_1).isEqualTo(true)
        assertThat(GobbletTier.TIER_3 canBeStackedOn GobbletTier.TIER_2).isEqualTo(true)
        assertThat(GobbletTier.TIER_3 canBeStackedOn GobbletTier.TIER_3).isEqualTo(false)

        // If tierToStack is null, then any tier can be stacked on it
        assertThat(GobbletTier.TIER_1 canBeStackedOn null).isEqualTo(true)
        assertThat(GobbletTier.TIER_2 canBeStackedOn null).isEqualTo(true)
        assertThat(GobbletTier.TIER_3 canBeStackedOn null).isEqualTo(true)
    }
}