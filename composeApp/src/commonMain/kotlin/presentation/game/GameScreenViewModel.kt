package presentation.game

import core.game.GameEngine
import core.mvvm.ViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val gameEngine: GameEngine
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameScreenUiState())
    val uiState = combine(
        _uiState,
        gameEngine.state
    ) { uiState, gameState ->
        uiState.copy(
            loading = false,
            gameState = gameState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GameScreenUiState(loading = true)
    )

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    gameType = gameEngine.gameType
                )
            }

            gameEngine.start()
        }
    }

    fun onEvent(event: GameScreenUiEvent) {
        when (event) {
            is GameScreenUiEvent.OnItemClick -> {
                viewModelScope.launch {
                    gameEngine.makeMove(
                        index = event.index,
                        tier = event.tier
                    )
                }
            }
            is GameScreenUiEvent.OnResetClick -> {
                viewModelScope.launch {
                    gameEngine.reset()
                }
            }
        }
    }
}
