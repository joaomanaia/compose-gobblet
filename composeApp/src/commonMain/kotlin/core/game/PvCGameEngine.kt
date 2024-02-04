package core.game

import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.*

class PvCGameEngine : ComputerGameEngine {
    override val gameType: GameType = GameType.PLAYER_VS_COMPUTER

    private val _state = MutableStateFlow(GameEngine.State())
    override val state = _state.asStateFlow()

    companion object {
        private const val ALGORITHM_MAX_DEPTH = 3

        /**
         * The value of each position on the board. The higher the value, the more valuable the position is.
         * This is used to evaluate the board based on the position of the pieces.
         */
        private val positionValue = listOf(
            3, 2, 3,
            2, 4, 2,
            3, 2, 3
        )
    }

    override suspend fun start() {
        _state.emit(GameEngine.State())
    }

    override suspend fun reset() {
        _state.emit(GameEngine.State())
    }

    override suspend fun makeMove(index: Int, tier: GobbletTier) {
        var newGameResult: GameResult? = null

        _state.update { currentState ->
            // Remove the piece from the player's inventory, if it's their turn
            val player1Items = if (currentState.isPlayer1Turn) {
                currentState.player1Items - tier
            } else {
                currentState.player1Items
            }

            val newBoard = currentState.board.insertGobbletAt(
                index = index,
                tier = tier,
                player = currentState.currentPlayer
            )

            val newPlayedMoves = currentState.playedMoves + 1
            newGameResult = newBoard.getGameResult(currentState.player2Items, newPlayedMoves)

            currentState.copy(
                board = newBoard,
                currentPlayer = currentState.currentPlayer.next(),
                player1Items = player1Items,
                playedMoves = newPlayedMoves
            )
        }

        // Check if the game is over, if so, return
        if (newGameResult != null) {
            return
        }

        // Wait a bit before making the computer move
        delay(500)

        makeComputerMove()
    }

    override suspend fun makeComputerMove() {
        _state.update { currentState ->
            val (index, tier) = getBestMove(
                board = currentState.board,
                playedMoves = currentState.playedMoves,
                currentPlayerGobblets = currentState.player2Items,
                depth = ALGORITHM_MAX_DEPTH
            ) ?: return@update currentState

            // Remove the piece from the player's inventory
            val player2Items = currentState.player2Items - tier

            currentState.copy(
                board = currentState.board.insertGobbletAt(
                    index = index,
                    tier = tier,
                    player = currentState.currentPlayer
                ),
                currentPlayer = currentState.currentPlayer.next(),
                player2Items = player2Items,
                playedMoves = currentState.playedMoves + 1
            )
        }
    }

    fun getBestMove(
        board: GobbletBoard,
        playedMoves: Int,
        currentPlayerGobblets: List<GobbletTier>,
        depth: Int,
    ): Pair<Int, GobbletTier>? {
        var bestMove: Pair<Int, GobbletTier>? = null
        var bestEval = Int.MIN_VALUE

        // Convert the list to a set to avoid duplicates in the for loop, since
        // we don't want to consider the same gobblet multiple times
        val playerGobblets = currentPlayerGobblets.toSet()

        for (i in 0 until board.size) {
            for (tier in playerGobblets) {
                if (board.canInsertGobbletAt(i, tier)) {
                    val newBoard = board.insertGobbletAt(i, tier, Player.PLAYER_2)
                    val newCurrentPlayerGobblets = currentPlayerGobblets - tier

                    val eval = minimax(
                        board = newBoard,
                        playedMoves = playedMoves + 1,
                        currentPlayerGobblets = newCurrentPlayerGobblets,
                        depth = depth - 1,
                        alpha = Int.MIN_VALUE,
                        beta = Int.MAX_VALUE,
                        isMaximizing = false
                    )

                    if (eval > bestEval) {
                        // println("Best move: $i to $tier with eval: $eval")
                        // println("Board:")
                        // println(newBoard.toBoardString())

                        bestEval = eval
                        bestMove = i to tier
                    }
                }
            }
        }

        return bestMove
    }

    /**
     * Evaluate the board based on the current state of the game
     *
     * @param gameResult The result of the game (if it's over)
     * @return The evaluation of the board
     */
    internal fun evaluateBoard(
        gobblets: List<GobbletBoardItem?>,
        gameResult: GameResult?,
    ): Int {
        return when {
            // Won by the computer
            gameResult?.wasWonBy(Player.PLAYER_2) == true -> 100
            // Won by the human
            gameResult?.wasWonBy(Player.PLAYER_1) == true -> -100
            gameResult is GameResult.Tie -> 0
            else -> {
                // This is a heuristic evaluation of the board, based on the number of pieces
                // the computer has compared to the human.
                // TODO: Improve the heuristic evaluation

                val positionScore = calculatePositionScore(gobblets)
                val potentialMovesScore = calculatePotentialMovesScore(gobblets)

                positionScore + potentialMovesScore
            }
        }
    }

    private fun calculatePositionScore(gobblets: List<GobbletBoardItem?>): Int {
        var score = 0

        gobblets.fastForEachIndexed { i, gobblet ->
            if (gobblet != null) {
                val positionScore = positionValue[i]
                // If the computer has the gobblet at this position, add the position score to the total score
                score += if (gobblet.player == Player.PLAYER_2) positionScore else -positionScore
            }
        }

        return score
    }

    private fun calculatePotentialMovesScore(gobblets: List<GobbletBoardItem?>): Int {
        val computerPotentialMoves = gobblets.count { it == null || it.player == Player.PLAYER_2 }
        val humanPotentialMoves = gobblets.count { it == null || it.player == Player.PLAYER_1 }

        return computerPotentialMoves - humanPotentialMoves
    }

    private fun minimax(
        board: GobbletBoard,
        playedMoves: Int,
        currentPlayerGobblets: List<GobbletTier>,
        depth: Int,
        alpha: Int,
        beta: Int,
        isMaximizing: Boolean,
    ): Int {
        val gameResult = board.getGameResult(currentPlayerGobblets, playedMoves)

        if (depth == 0 || gameResult != null) {
            return evaluateBoard(
                gobblets = board.gobblets,
                gameResult = gameResult
            )
        }

        if (isMaximizing) {
            var maxEval = Int.MIN_VALUE
            var _alpha = alpha

            for (i in 0 until board.size) {
                for (tier in currentPlayerGobblets) {
                    if (board.canInsertGobbletAt(i, tier)) {
                        val newBoard = board.insertGobbletAt(i, tier, Player.PLAYER_2)
                        val newCurrentPlayerGobblets = currentPlayerGobblets - tier

                        val eval = minimax(
                            board = newBoard,
                            playedMoves = playedMoves + 1,
                            currentPlayerGobblets = newCurrentPlayerGobblets,
                            depth = depth - 1,
                            alpha = _alpha,
                            beta = beta,
                            isMaximizing = false
                        )

                        maxEval = maxOf(maxEval, eval)

                        if (maxEval >= beta) {
                            break
                        }

                        _alpha = maxOf(_alpha, eval)
                    }
                }
            }

            return maxEval
        } else {
            var minEval = Int.MAX_VALUE
            var _beta = beta

            for (i in 0 until board.size) {
                for (tier in currentPlayerGobblets) {
                    if (board.canInsertGobbletAt(i, tier)) {
                        val newBoard = board.insertGobbletAt(i, tier, Player.PLAYER_1)
                        val newCurrentPlayerGobblets = currentPlayerGobblets - tier

                        val eval = minimax(
                            board = newBoard,
                            playedMoves = playedMoves + 1,
                            currentPlayerGobblets = newCurrentPlayerGobblets,
                            depth = depth - 1,
                            alpha = alpha,
                            beta = _beta,
                            isMaximizing = true
                        )
                        minEval = minOf(minEval, eval)

                        if (minEval <= alpha) {
                            break
                        }

                        _beta = minOf(_beta, eval)
                    }
                }
            }

            return minEval
        }
    }
}
