package model

import androidx.compose.ui.graphics.vector.ImageVector
import core.icons.GobbletTierIcons
import core.icons.gobblettiericons.Tier1
import core.icons.gobblettiericons.Tier2
import core.icons.gobblettiericons.Tier3

/**
 * Represents the tier of a gobblet.
 *
 * There are 3 tiers of gobblets, with tier 1 being the smallest and tier 3 being the largest.
 * A gobblet can be stacked on top of another gobblet if and only if its tier is greater than the
 * tier of the gobblet it is being stacked on.
 *
 * @property tier The tier of the gobblet.
 */
@JvmInline
value class GobbletTier private constructor(
    val tier: Int
) : Comparable<GobbletTier> {
    init {
        require(tier in 1..3) { "Invalid tier: $tier" }
    }

    companion object {
        val TIER_1 = GobbletTier(1)
        val TIER_2 = GobbletTier(2)
        val TIER_3 = GobbletTier(3)

        @JvmField
        val allTiers = listOf(TIER_1, TIER_2, TIER_3)
    }

    val icon: ImageVector
        get() = when (tier) {
            1 -> GobbletTierIcons.Tier1
            2 -> GobbletTierIcons.Tier2
            3 -> GobbletTierIcons.Tier3
            else -> throw IllegalArgumentException("Invalid tier: $tier")
        }

    override fun compareTo(other: GobbletTier): Int {
        return tier.compareTo(other.tier)
    }


    infix fun canBeStackedOn(other: GobbletTier): Boolean {
        return tier > other.tier
    }
}
