package presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import core.presentation.theme.spacing
import model.Player
import presentation.game.components.TierRowItems
import core.LongPressDraggable
import core.presentation.theme.TierColors
import model.GameResult
import model.GameType
import presentation.components.button.BackIconButton
import presentation.game.components.GobbletBoardComponent

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
fun GameScreen(
    viewModel: GameScreenViewModel,
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GameScreen(
        windowSizeClass = windowSizeClass,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick
    )
}

@Composable
@ExperimentalMaterial3Api
private fun GameScreen(
    windowSizeClass: WindowSizeClass,
    uiState: GameScreenUiState,
    tierColors: TierColors = TierColors.defaultTierColors(),
    onEvent: (event: GameScreenUiEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val mediumSpacing = MaterialTheme.spacing.medium

    val rowLayout = remember(windowSizeClass) {
        windowSizeClass.widthSizeClass > WindowWidthSizeClass.Medium
    }

    val gameState = uiState.gameState

    val gameResult by remember(
        gameState.board,
        gameState.isPlayer1Turn,
        gameState.player1Items,
        gameState.player2Items,
        gameState.playedMoves
    ) {
        derivedStateOf {
            val currentPlayerGobblets = if (gameState.isPlayer1Turn) {
                gameState.player1Items
            } else {
                gameState.player2Items
            }

            gameState.board.getGameResult(
                currentPlayerGobblets = currentPlayerGobblets,
                playedMoves = gameState.playedMoves
            )
        }
    }

    val isGameEnded = remember(gameResult) {
        gameResult != null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gobblet") },
                navigationIcon = { BackIconButton(onBackClick = onBackClick) },
                actions = {
                    IconButton(
                        onClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
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
                val (player1, player2, board, tieContent) = createRefs()

                if (gameResult == null || gameResult?.wasWonBy(Player.PLAYER_1) == true) {
                    val enabled = remember(gameState.isPlayer1Turn, isGameEnded, uiState.gameType) {
                        gameState.isPlayer1Turn && !isGameEnded
                    }

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
                        items = gameState.player1Items,
                        player = Player.PLAYER_1,
                        rowLayout = rowLayout,
                        gameResult = gameResult,
                        enabled = enabled,
                        tierColors = tierColors,
                        onPlayAgainClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    )
                }

                GobbletBoardComponent(
                    modifier = Modifier.constrainAs(board) {
                        if (rowLayout) {
                            if (gameResult is GameResult.Tie) {
                                top.linkTo(tieContent.bottom, margin = mediumSpacing)
                            } else {
                                top.linkTo(parent.top)
                            }

                            bottom.linkTo(parent.bottom)

                            when {
                                gameResult?.wasWonBy(Player.PLAYER_1) == true -> {
                                    start.linkTo(player1.end, margin = mediumSpacing)
                                    end.linkTo(parent.end)
                                }
                                gameResult?.wasWonBy(Player.PLAYER_2) == true -> {
                                    start.linkTo(parent.start)
                                    end.linkTo(player2.start, margin = mediumSpacing)
                                }
                                gameResult is GameResult.Tie -> {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                // Ongoing game
                                else -> {
                                    start.linkTo(player1.end, margin = mediumSpacing)
                                    end.linkTo(player2.start, margin = mediumSpacing)
                                }
                            }
                        } else {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            when {
                                gameResult?.wasWonBy(Player.PLAYER_1) == true -> {
                                    top.linkTo(player1.bottom, margin = mediumSpacing)
                                    bottom.linkTo(parent.bottom)
                                }
                                gameResult?.wasWonBy(Player.PLAYER_2) == true -> {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(player2.top, margin = mediumSpacing)
                                }
                                gameResult is GameResult.Tie -> {
                                    top.linkTo(tieContent.bottom, margin = mediumSpacing)
                                    bottom.linkTo(parent.bottom)
                                }
                                // Ongoing game
                                else -> {
                                    top.linkTo(player1.bottom, margin = mediumSpacing)
                                    bottom.linkTo(player2.top, margin = mediumSpacing)
                                }
                            }
                        }

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    currentPlayer = gameState.currentPlayer,
                    boardGobblets = gameState.board.gobblets,
                    gameResult = gameResult,
                    tierColors = tierColors,
                    onItemDrop = { index, tier ->
                        onEvent(GameScreenUiEvent.OnItemClick(index, tier))
                    }
                )

                if (gameResult == null || gameResult?.wasWonBy(Player.PLAYER_2) == true) {
                    val enabled = remember(gameState.isPlayer2Turn, isGameEnded, uiState.gameType) {
                        gameState.isPlayer2Turn && !isGameEnded && uiState.gameType == GameType.PLAYER_VS_PLAYER
                    }

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
                        items = gameState.player2Items,
                        player = Player.PLAYER_2,
                        rowLayout = rowLayout,
                        gameResult = gameResult,
                        enabled = enabled,
                        tierColors = tierColors,
                        onPlayAgainClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    )
                }

                if (isGameEnded && gameResult is GameResult.Tie) {
                    TieContent(
                        modifier = Modifier.constrainAs(tieContent) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            width = Dimension.fillToConstraints
                        },
                        onPlayAgainClick = { onEvent(GameScreenUiEvent.OnResetClick) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TieContent(
    modifier: Modifier = Modifier,
    onPlayAgainClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacing.large,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(text = "It's a tie!")
            Button(
                onClick = onPlayAgainClick,
            ) {
                Text(text = "Play again")
            }
        }
    }
}
