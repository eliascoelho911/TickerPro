package com.eliascoelho911.tickerpro.transaction.presentation

import androidx.compose.runtime.Immutable
import com.eliascoelho911.tickerpro.core.arch.UIAction

@Immutable
sealed interface NewTransactionAction : UIAction {
    data object NavigateBack : NewTransactionAction
    data object TransactionSaved : NewTransactionAction
    data class ShowError(val message: String) : NewTransactionAction
}