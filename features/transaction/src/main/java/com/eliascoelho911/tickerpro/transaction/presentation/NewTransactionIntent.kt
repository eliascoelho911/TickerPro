package com.eliascoelho911.tickerpro.transaction.presentation

import androidx.compose.runtime.Immutable
import com.eliascoelho911.tickerpro.core.arch.UIIntent
import com.eliascoelho911.tickerpro.transaction.domain.model.Asset
import com.eliascoelho911.tickerpro.transaction.domain.model.TransactionType
import java.time.LocalDate

@Immutable
sealed interface NewTransactionIntent : UIIntent {
    data class SearchAssets(val query: String) : NewTransactionIntent
    data class SelectAsset(val asset: Asset) : NewTransactionIntent
    data class ChangeTransactionType(val type: TransactionType) : NewTransactionIntent
    data class ChangeQuantity(val quantity: String) : NewTransactionIntent
    data class ChangePrice(val price: String) : NewTransactionIntent
    data class ChangeDate(val date: LocalDate) : NewTransactionIntent
    data class ChangeFees(val fees: String) : NewTransactionIntent
    data class ChangeNotes(val notes: String) : NewTransactionIntent
    data object ValidateForm : NewTransactionIntent
    data object ShowConfirmation : NewTransactionIntent
    data object HideConfirmation : NewTransactionIntent
    data object SaveTransaction : NewTransactionIntent
    data object ClearForm : NewTransactionIntent
}