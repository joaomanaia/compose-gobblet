package core.mvvm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlin.native.concurrent.ThreadLocal

/**
 * Factory of viewModelScope. Internal API, for ability of mvvm-test to change viewModelScope
 * dispatcher.
 *
 * In default implementation create main-thread dispatcher scope.
 */
@ThreadLocal
var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(createUIDispatcher())
}
