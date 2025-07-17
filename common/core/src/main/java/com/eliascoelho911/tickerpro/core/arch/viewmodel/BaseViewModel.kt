package com.eliascoelho911.tickerpro.core.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliascoelho911.tickerpro.core.arch.UIAction
import com.eliascoelho911.tickerpro.core.arch.UIIntent
import com.eliascoelho911.tickerpro.core.arch.UIState
import com.eliascoelho911.logs.AndroidLogger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<ACTION : UIAction, STATE : UIState, INTENT : UIIntent>(initialState: STATE) :
    ViewModel() {

    protected val logger: AndroidLogger = AndroidLogger(this::class.java.simpleName)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _action = MutableSharedFlow<ACTION>(replay = 0)
    val action: SharedFlow<ACTION> = _action.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        replay = 0
    )

    @JvmName("onIntent2")
    fun onIntent(intent: INTENT) {
        logger.debug("Intent received: $intent")
        intent.onIntent()
    }

    protected abstract fun INTENT.onIntent()

    protected fun updateState(update: (STATE) -> STATE) {
        val newState = update(state.value)
        logger.debug("State updated: $newState")
        _state.update { newState }
    }

    protected suspend fun sendAction(action: ACTION) {
        logger.debug("Action sent: $action")
        _action.emit(action)
    }
}

fun <STATE : UIState> BaseViewModel<*, STATE, *>.stateValue(): STATE = state.value