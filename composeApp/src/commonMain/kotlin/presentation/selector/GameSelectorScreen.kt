package presentation.selector

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import core.presentation.theme.spacing
import model.GameType

@Composable
@ExperimentalMaterial3Api
internal fun GameSelectorScreen(
    navigateToGame: (GameType) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacing.large,
                alignment = Alignment.CenterVertically
            )
        ) {
            GameCard(
                gameType = GameType.PLAYER_VS_PLAYER,
                onClick = { navigateToGame(GameType.PLAYER_VS_PLAYER) }
            )
            GameCard(
                gameType = GameType.PLAYER_VS_COMPUTER,
                onClick = { navigateToGame(GameType.PLAYER_VS_COMPUTER) }
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
private fun GameCard(
    modifier: Modifier = Modifier,
    gameType: GameType,
    onClick: () -> Unit
) {
    GameCard(
        modifier = modifier.fillMaxWidth(0.7f),
        title = when (gameType) {
            GameType.PLAYER_VS_PLAYER -> "Player vs Player"
            GameType.PLAYER_VS_COMPUTER -> "Player vs Computer"
        },
        onClick = onClick
    )
}

@Composable
@ExperimentalMaterial3Api
private fun GameCard(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.spacing.large,
                    horizontal = MaterialTheme.spacing.large
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
