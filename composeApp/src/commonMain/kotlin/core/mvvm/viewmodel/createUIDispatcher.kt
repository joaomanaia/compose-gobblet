package core.mvvm.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * We use coroutines native-mt version, so we can use Main dispatcher on both platforms
 */
fun createUIDispatcher(): CoroutineDispatcher = Dispatchers.Main
