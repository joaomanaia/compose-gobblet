package core.mvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

actual open class ViewModel actual constructor() : ViewModel() {
    actual val viewModelScope: CoroutineScope =
        CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    public actual override fun onCleared() {
        super.onCleared()

        viewModelScope.cancel()
    }
}

internal class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}