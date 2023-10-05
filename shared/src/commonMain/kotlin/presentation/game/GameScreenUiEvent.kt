package presentation.game

sealed interface GameScreenUiEvent {
    data class OnItemClick(val index: Int) : GameScreenUiEvent

    data object OnResetClick : GameScreenUiEvent
}