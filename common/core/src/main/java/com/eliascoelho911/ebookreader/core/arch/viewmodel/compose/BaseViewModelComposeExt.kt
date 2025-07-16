package com.eliascoelho911.ebookreader.core.arch.viewmodel.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.eliascoelho911.ebookreader.core.arch.UIAction
import com.eliascoelho911.ebookreader.core.arch.UIIntent
import com.eliascoelho911.ebookreader.core.arch.UIState
import com.eliascoelho911.ebookreader.core.arch.viewmodel.BaseViewModel

@SuppressLint("ComposableNaming")
@Composable
fun <ACTION: UIAction, STATE: UIState, INTENT: UIIntent> BaseViewModel<ACTION, STATE, INTENT>.collectAction(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (suspend ACTION.() -> Unit)
) {
    val actionFlow = this.action
    val lifecycleOwner = LocalLifecycleOwner.current

    val callback by rememberUpdatedState(newValue = action)

    LaunchedEffect(actionFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            actionFlow.collect { callback(it) }
        }
    }
}