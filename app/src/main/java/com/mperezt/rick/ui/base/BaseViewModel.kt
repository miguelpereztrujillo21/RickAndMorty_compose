package com.mperezt.rick.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<STATE : Any> (
    initialState: STATE
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state


    fun <T> executeUseCase(
        useCase: suspend () -> T,
        success: (T) -> Unit,
        error: (Throwable) -> Unit = { Log.e(this::class.java.simpleName, it.message ?: "Unknown error") }
    ) {
        viewModelScope.launch {
            try {
                val result = useCase()
                success(result)
            } catch (t: Throwable) {
                error(t)
            }
        }
    }
}

