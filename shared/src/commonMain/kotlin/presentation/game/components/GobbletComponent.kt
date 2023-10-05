package presentation.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.icons.GobbletTierIcons
import core.icons.gobblettiericons.Tier1
import core.icons.gobblettiericons.Tier2
import core.icons.gobblettiericons.Tier3
import model.GobbletTier
import model.Player

@Composable
internal fun GobbletComponent(
    modifier: Modifier = Modifier,
    tier: GobbletTier,
    player: Player,
    size: Dp = DEFAULT_TIER_SIZE,
    onClick: () -> Unit = {},
) {
    val backgroundColor = when (player) {
        Player.PLAYER_1 -> MaterialTheme.colorScheme.primaryContainer
        Player.PLAYER_2 -> MaterialTheme.colorScheme.secondaryContainer
    }

    val contentColor = when (player) {
        Player.PLAYER_1 -> MaterialTheme.colorScheme.onPrimaryContainer
        Player.PLAYER_2 -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = contentColor,
        contentColor = backgroundColor,
        onClick = onClick
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(size / BORDER_PADDING_DIVISOR), // Makes a responsive border size
            shape = CircleShape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(size / ICON_PADDING_DIVISOR), // Makes a responsive icon size
                contentDescription = null,
                imageVector = tier.getIcon()
            )
        }
    }
}

private fun GobbletTier.getIcon(): ImageVector = when (this) {
    GobbletTier.TIER_1 -> GobbletTierIcons.Tier1
    GobbletTier.TIER_2 -> GobbletTierIcons.Tier2
    GobbletTier.TIER_3 -> GobbletTierIcons.Tier3
}

private val DEFAULT_TIER_SIZE = 64.dp

private const val BORDER_PADDING_DIVISOR = 16f
private const val ICON_PADDING_DIVISOR = 5f
