package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
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
        windowSizeClass.widthSizeClass > WindowWidthSizeClass.Medium
    }

    val winner = uiState.board.winner

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

                if (winner == null || winner.first == Player.PLAYER_1) {
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
                        winner = winner,
                        enabled = uiState.isPlayer1Turn && !uiState.board.isGameEnded,
                        onPlayAgainClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    )
                }

                GobbletBoardComponent(
                    modifier = Modifier.constrainAs(board) {
                        if (rowLayout) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)

                            if (winner != null) {
                                if (winner.first == Player.PLAYER_1) {
                                    start.linkTo(player1.end, margin = mediumSpacing)
                                    end.linkTo(parent.end)
                                } else {
                                    start.linkTo(parent.start)
                                    end.linkTo(player2.start, margin = mediumSpacing)
                                }
                            } else {
                                start.linkTo(player1.end, margin = mediumSpacing)
                                end.linkTo(player2.start, margin = mediumSpacing)
                            }

                            width = Dimension.fillToConstraints
                        } else {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            if (winner != null) {
                                if (winner.first == Player.PLAYER_1) {
                                    top.linkTo(player1.bottom, margin = mediumSpacing)
                                    bottom.linkTo(parent.bottom)
                                } else {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(player2.top, margin = mediumSpacing)
                                }
                            } else {
                                top.linkTo(player1.bottom, margin = mediumSpacing)
                                bottom.linkTo(player2.top, margin = mediumSpacing)
                            }

                            height = Dimension.fillToConstraints
                        }
                    },
                    boardGobblets = uiState.board.gobblets,
                    winner = winner,
                    onItemDrop = { index, tier ->
                        onEvent(GameScreenUiEvent.OnItemClick(index, tier))
                    }
                )

                if (winner == null || winner.first == Player.PLAYER_2) {
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
                        winner = winner,
                        enabled = uiState.isPlayer2Turn && !uiState.board.isGameEnded,
                        onPlayAgainClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    )
                }
            }
        }
    }
}
