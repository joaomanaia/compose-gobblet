package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import core.presentation.theme.spacing
import model.Player
import presentation.game.components.GobbletBoardComponent
import presentation.game.components.TierRowItems
import core.LongPressDraggable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GameScreen(
    viewModel: GameScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    GameScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
@ExperimentalMaterial3Api
private fun GameScreen(
    uiState: GameScreenUiState,
    onEvent: (event: GameScreenUiEvent) -> Unit
) {
    val mediumSpacing = MaterialTheme.spacing.medium

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gobblet") },
                actions = {
                    IconButton(
                        onClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LongPressDraggable(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(mediumSpacing)
            ) {
                if (uiState.board.isGameEnded) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = mediumSpacing),
                        text = "Winner: ${uiState.board.winner?.name ?: "Tie"}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                TierRowItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumSpacing)
                        .padding(top = mediumSpacing),
                    items = uiState.player1Items,
                    player = Player.PLAYER_1,
                    enabled = uiState.isPlayer1Turn && !uiState.board.isGameEnded
                )

                GobbletBoardComponent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = mediumSpacing),
                    boardGobblets = uiState.board.gobblets,
                    onItemDrop = { index, tier ->
                        onEvent(GameScreenUiEvent.OnItemClick(index, tier))
                    }
                )

                TierRowItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumSpacing)
                        .padding(bottom = mediumSpacing),
                    items = uiState.player2Items,
                    player = Player.PLAYER_2,
                    enabled = uiState.isPlayer2Turn && !uiState.board.isGameEnded
                )
            }
        }
    }
}
