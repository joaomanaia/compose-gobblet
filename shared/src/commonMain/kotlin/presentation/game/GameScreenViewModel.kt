package presentation.game

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.GobbletBoardItem

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: GameScreenUiEvent) {
        when (event) {
            is GameScreenUiEvent.OnItemClick -> {
                _uiState.update { currentState ->
                    // Update the board with the new gobblet
                    val newBoardGobblets = currentState.boardGobblets.toMutableList()
                    newBoardGobblets[event.index] = GobbletBoardItem(
                        tier = event.tier,
                        player = currentState.currentPlayer
                    )

                    // Remove the piece from the player's inventory, if it's their turn
                    val player1Items = if (currentState.isPlayer1Turn) {
                        currentState.player1Items - event.tier
                    } else {
                        currentState.player1Items
                    }

                    val player2Items = if (currentState.isPlayer2Turn) {
                        currentState.player2Items - event.tier
                    } else {
                        currentState.player2Items
                    }

                    currentState.copy(
                        boardGobblets = newBoardGobblets,
                        currentPlayer = currentState.currentPlayer.next(),
                        player1Items = player1Items,
                        player2Items = player2Items
                    )
                }
            }
            is GameScreenUiEvent.OnResetClick -> {
                viewModelScope.launch {
                    _uiState.emit(GameScreenUiState())
                }
            }
        }
    }
}