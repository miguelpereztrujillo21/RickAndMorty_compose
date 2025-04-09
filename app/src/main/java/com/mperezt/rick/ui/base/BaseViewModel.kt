package com.mperezt.rick.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<STATE : Any, EVENT> (
    initialState: STATE
) : ViewModel() {

    // Estado persistente, se usa para representar la UI (loading, data, error)
    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    // Eventos transitorios (navegación, toasts, etc.)
    private val _event = MutableSharedFlow<EVENT>()
    val event: SharedFlow<EVENT> = _event

    // Lógica para emitir eventos transitorios
    protected suspend fun emitEvent(event: EVENT) {
        _event.emit(event)
    }

    // Lógica para ejecutar casos de uso y actualizar el estado
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

