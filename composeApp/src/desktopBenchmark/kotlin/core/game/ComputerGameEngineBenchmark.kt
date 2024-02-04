package core.game

import model.*
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Defaults
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = Defaults.WARMUP_ITERATIONS, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class ComputerGameEngineBenchmark {
    private lateinit var computerGameEngine: PvCGameEngine

    private var board: GobbletBoard = GobbletBoard.empty()
    private val gobblets: MutableList<GobbletBoardItem?> = mutableListOf()
    private val currentUserGobblets: MutableList<GobbletTier> = mutableListOf()
    private var gameResult: GameResult? = null

    @Setup
    fun setUp() {
        computerGameEngine = PvCGameEngine()

        board = GobbletBoard(
            gobblets = listOf(
                GobbletBoardItem(GobbletTier.TIER_3, Player.PLAYER_1),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )

        gobblets.clear()
        gobblets.addAll(board.gobblets)
        gameResult = board.getGameResult(currentPlayerGobblets = emptyList(), playedMoves = 1)

        currentUserGobblets.clear()
        currentUserGobblets.addAll(
            listOf(
                GobbletTier.TIER_1,
                GobbletTier.TIER_1,
                GobbletTier.TIER_1,
                GobbletTier.TIER_2,
                GobbletTier.TIER_2,
                GobbletTier.TIER_2,
                GobbletTier.TIER_3,
                GobbletTier.TIER_3,
                GobbletTier.TIER_3,
            )
        )
    }

    @Benchmark
    fun getBestMoveBenchmark(): Pair<Int, GobbletTier>? {
        return computerGameEngine.getBestMove(
            board = board,
            currentPlayerGobblets = currentUserGobblets,
            depth = 3,
            playedMoves = 1
        )
    }
}
