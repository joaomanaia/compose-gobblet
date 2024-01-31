package core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class TierColors internal constructor(
    val player1Color: Color,
    val onPlayer1Color: Color,
    val player1ContainerColor: Color,
    val onPlayer1ContainerColor: Color,
    val player2Color: Color,
    val onPlayer2Color: Color,
    val player2ContainerColor: Color,
    val onPlayer2ContainerColor: Color,
) {
    companion object {
        @Composable
        fun defaultTierColors(
            player1Color: Color = MaterialTheme.colorScheme.primary,
            onPlayer1Color: Color = contentColorFor(player1Color),
            player1ContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
            onPlayer1ContainerColor: Color = contentColorFor(player1ContainerColor),
            player2Color: Color = MaterialTheme.colorScheme.tertiary,
            onPlayer2Color: Color = contentColorFor(player2Color),
            player2ContainerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
            onPlayer2ContainerColor: Color = contentColorFor(player2ContainerColor),
        ): TierColors = TierColors(
            player1Color = player1Color,
            onPlayer1Color = onPlayer1Color,
            player1ContainerColor = player1ContainerColor,
            onPlayer1ContainerColor = onPlayer1ContainerColor,
            player2Color = player2Color,
            onPlayer2Color = onPlayer2Color,
            player2ContainerColor = player2ContainerColor,
            onPlayer2ContainerColor = onPlayer2ContainerColor,
        )
    }
}
