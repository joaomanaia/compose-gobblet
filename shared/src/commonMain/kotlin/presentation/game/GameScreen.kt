package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import core.presentation.theme.spacing
import model.Player
import org.koin.compose.koinInject
import presentation.game.components.GobbletBoard
import presentation.game.components.TierRowItems
import core.LongPressDraggable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun GameScreen(
    viewModel: GameScreenViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    GameScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
@ExperimentalMaterial3Api
internal fun GameScreen(
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
                TierRowItems(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumSpacing)
                        .padding(top = mediumSpacing),
                    items = uiState.player1Items,
                    player = Player.PLAYER_1,
                    enabled = uiState.isPlayer1Turn
                )

                GobbletBoard(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = mediumSpacing),
                    boardGobblets = uiState.boardGobblets,
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
                    enabled = uiState.isPlayer2Turn
                )
            }
        }
    }
}
