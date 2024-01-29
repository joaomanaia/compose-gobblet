package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import core.presentation.theme.spacing
import model.Player
import presentation.game.components.TierRowItems
import core.LongPressDraggable
import presentation.game.components.GobbletBoardComponent

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
fun GameScreen(
    viewModel: GameScreenViewModel,
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass()
) {
    val uiState by viewModel.uiState.collectAsState()

    GameScreen(
        windowSizeClass = windowSizeClass,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
@ExperimentalMaterial3Api
private fun GameScreen(
    windowSizeClass: WindowSizeClass,
    uiState: GameScreenUiState,
    onEvent: (event: GameScreenUiEvent) -> Unit
) {
    val mediumSpacing = MaterialTheme.spacing.medium

    val rowLayout = remember(windowSizeClass) {
        windowSizeClass.heightSizeClass < WindowHeightSizeClass.Medium
    }

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
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .then(
                        Modifier.padding(
                            start = mediumSpacing,
                            end = mediumSpacing,
                            bottom = mediumSpacing
                        )
                    ),
            ) {
                val (player1, player2, board) = createRefs()

                TierRowItems(
                    modifier = Modifier.constrainAs(player1) {
                        if (rowLayout) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)

                            height = Dimension.fillToConstraints
                        } else {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            width = Dimension.fillToConstraints
                        }
                    },
                    items = uiState.player1Items,
                    player = Player.PLAYER_1,
                    rowLayout = rowLayout,
                    enabled = uiState.isPlayer1Turn && !uiState.board.isGameEnded
                )

                GobbletBoardComponent(
                    modifier = Modifier.constrainAs(board) {
                        if (rowLayout) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(player1.end, margin = mediumSpacing)
                            end.linkTo(player2.start, margin = mediumSpacing)

                            width = Dimension.fillToConstraints
                        } else {
                            top.linkTo(player1.bottom, margin = mediumSpacing)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(player2.top, margin = mediumSpacing)

                            height = Dimension.fillToConstraints
                        }
                    },
                    boardGobblets = uiState.board.gobblets,
                    onItemDrop = { index, tier ->
                        onEvent(GameScreenUiEvent.OnItemClick(index, tier))
                    }
                )

                TierRowItems(
                    modifier = Modifier.constrainAs(player2) {
                        if (rowLayout) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)

                            height = Dimension.fillToConstraints
                        } else {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)

                            width = Dimension.fillToConstraints
                        }
                    },
                    items = uiState.player2Items,
                    player = Player.PLAYER_2,
                    rowLayout = rowLayout,
                    enabled = uiState.isPlayer2Turn && !uiState.board.isGameEnded
                )
            }
        }
    }
}
