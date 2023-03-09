package com.test.testtasknews.extension

import kotlinx.coroutines.*

inline fun CoroutineScope.launchIO(
    crossinline safeAction: suspend () -> Unit,
    crossinline onError: suspend (Throwable) -> Unit,
    errorDispatcher: CoroutineDispatcher = Dispatchers.Main
): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        launch(errorDispatcher) {
            onError.invoke(throwable)
        }
    }
    return this.launch(exceptionHandler + Dispatchers.IO) {
        safeAction.invoke()
    }
}