package presentation.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import core.presentation.theme.TierColors
import model.GobbletTier
import model.Player

@Composable
internal fun GobbletComponent(
    modifier: Modifier = Modifier,
    tier: GobbletTier,
    player: Player,
    enabled: Boolean = true,
    colors: GobbletComponentColors = GobbletComponentDefaults.colors()
) {
    val outlineColor = colors.outlineColor(player, enabled).value
    val innerColor = colors.containerColor(player, enabled).value
    val contentColor = colors.contentColor(player, enabled).value

    val icon = rememberVectorPainter(tier.icon)

    Canvas(
        modifier = modifier
            .size(DEFAULT_TIER_SIZE)
            .aspectRatio(1f),
    ) {
        drawGobbletComponent(
            outlineColor = outlineColor,
            innerColor = innerColor,
            contentColor = contentColor,
            icon = icon
        )
    }
}

internal fun DrawScope.drawGobbletComponent(
    outlineColor: Color,
    innerColor: Color,
    contentColor: Color,
    icon: VectorPainter
) {
    // Width and height are the same, so we can use either
    val radius = maxOf(size.width, size.height) / 2f

    // Draw the outline first, so it's behind the container
    drawCircle(
        color = outlineColor,
        radius = radius,
        center = center
    )

    // Calculate the padding for the inner container to make the border responsive.
    // Increase the divisor value to make the border smaller.
    val innerContainerPadding = radius / BORDER_PADDING_DIVISOR

    // Draw the inner container
    drawCircle(
        color = innerColor,
        radius = radius - innerContainerPadding,
        center = center,
    )

    with(icon) {
        inset(
            // Center the icon in the container
            horizontal = radius / 2f,
            vertical = radius / 2f
        ) {
            draw(
                size = Size(
                    width = radius,
                    height = radius
                ),
                colorFilter = ColorFilter.tint(contentColor)
            )
        }
    }
}

private val DEFAULT_TIER_SIZE = 64.dp

private const val BORDER_PADDING_DIVISOR = 8f

object GobbletComponentDefaults {
    @Composable
    fun colors(
        tierColors: TierColors = TierColors.defaultTierColors(),
        disabledContainerColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
        disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledOutlineColor: Color = disabledContentColor
    ): GobbletComponentColors = GobbletComponentColors(
        player1ContainerColor = tierColors.player1ContainerColor,
        player2ContainerColor = tierColors.player2ContainerColor,
        player1ContentColor = tierColors.player1Color,
        player2ContentColor = tierColors.player2Color,
        player1OutlineColor = tierColors.player1Color,
        player2OutlineColor = tierColors.player2Color,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
        disabledOutlineColor = disabledOutlineColor
    )
}

@Immutable
class GobbletComponentColors internal constructor(
    private val player1ContainerColor: Color,
    private val player2ContainerColor: Color,
    private val player1ContentColor: Color,
    private val player2ContentColor: Color,
    private val player1OutlineColor: Color,
    private val player2OutlineColor: Color,
    private val disabledContainerColor: Color,
    private val disabledContentColor: Color,
    private val disabledOutlineColor: Color
) {
    @Composable
    fun outlineColor(
        player: Player,
        enabled: Boolean = true
    ): State<Color> = rememberUpdatedState(
        newValue = if (enabled) {
            when (player) {
                Player.PLAYER_1 -> player1OutlineColor
                Player.PLAYER_2 -> player2OutlineColor
            }
        } else {
            disabledOutlineColor
        }
    )

    @Composable
    fun containerColor(
        player: Player,
        enabled: Boolean = true
    ): State<Color> = rememberUpdatedState(
        newValue = if (enabled) {
            when (player) {
                Player.PLAYER_1 -> player1ContainerColor
                Player.PLAYER_2 -> player2ContainerColor
            }
        } else {
            disabledContainerColor
        }
    )

    @Composable
    fun contentColor(
        player: Player,
        enabled: Boolean = true
    ): State<Color> = rememberUpdatedState(
        newValue = if (enabled) {
            when (player) {
                Player.PLAYER_1 -> player1ContentColor
                Player.PLAYER_2 -> player2ContentColor
            }
        } else {
            disabledContentColor
        }
    )
}
