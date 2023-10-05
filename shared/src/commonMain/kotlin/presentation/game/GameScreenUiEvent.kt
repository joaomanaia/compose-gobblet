package presentation.game

import model.GobbletTier

sealed interface GameScreenUiEvent {
    data class OnItemClick(
        val index: Int,
        val tier: GobbletTier
    ) : GameScreenUiEvent

    data object OnResetClick : GameScreenUiEvent
}