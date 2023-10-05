package presentation.game

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.GobbletBoardItem
import model.GobbletTier
import model.Player

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: GameScreenUiEvent) {
        when (event) {
            is GameScreenUiEvent.OnItemClick -> {
                _uiState.update { currentState ->
                    val newBoardGobblets = currentState.boardGobblets.toMutableList()

                    newBoardGobblets[event.index] = GobbletBoardItem(
                        tier = GobbletTier.entries.random(),
                        player = Player.entries.random()
                    )

                    currentState.copy(boardGobblets = newBoardGobblets)
                }
            }
            is GameScreenUiEvent.OnResetClick -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        boardGobblets = emptyBoardList(currentState.boardSize)
                    )
                }
            }
        }
    }
}