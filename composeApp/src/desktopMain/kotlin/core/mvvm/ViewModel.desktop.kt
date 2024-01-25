package core.mvvm

import core.mvvm.viewmodel.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope = createViewModelScope()

    actual open fun onCleared() {
        viewModelScope.cancel()
    }
}
