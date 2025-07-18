package com.eliascoelho911.tickerpro.transaction.presentation

import androidx.lifecycle.viewModelScope
import com.eliascoelho911.tickerpro.core.arch.viewmodel.BaseViewModel
import com.eliascoelho911.tickerpro.transaction.domain.model.Asset
import com.eliascoelho911.tickerpro.transaction.domain.model.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

class NewTransactionViewModel : BaseViewModel<NewTransactionAction, NewTransactionState, NewTransactionIntent>(
    initialState = NewTransactionState()
) {

    override fun NewTransactionIntent.onIntent() {
        when (this) {
            is NewTransactionIntent.SearchAssets -> searchAssets(query)
            is NewTransactionIntent.SelectAsset -> selectAsset(asset)
            is NewTransactionIntent.ChangeTransactionType -> changeTransactionType(type)
            is NewTransactionIntent.ChangeQuantity -> changeQuantity(quantity)
            is NewTransactionIntent.ChangePrice -> changePrice(price)
            is NewTransactionIntent.ChangeDate -> changeDate(date)
            is NewTransactionIntent.ChangeFees -> changeFees(fees)
            is NewTransactionIntent.ChangeNotes -> changeNotes(notes)
            is NewTransactionIntent.ValidateForm -> validateForm()
            is NewTransactionIntent.ShowConfirmation -> showConfirmation()
            is NewTransactionIntent.HideConfirmation -> hideConfirmation()
            is NewTransactionIntent.SaveTransaction -> saveTransaction()
            is NewTransactionIntent.ClearForm -> clearForm()
        }
    }

    private fun searchAssets(query: String) {
        updateState { it.copy(searchQuery = query, isLoading = true) }
        
        viewModelScope.launch {
            delay(300)
            
            val mockResults = if (query.isNotBlank()) {
                listOf(
                    Asset("PETR4", "PETR4", "Petrobras PN"),
                    Asset("VALE3", "VALE3", "Vale ON"),
                    Asset("ITUB4", "ITUB4", "Itaú Unibanco PN"),
                    Asset("BBDC4", "BBDC4", "Bradesco PN"),
                    Asset("ABEV3", "ABEV3", "Ambev ON")
                ).filter { 
                    it.symbol.contains(query, ignoreCase = true) || 
                    it.name.contains(query, ignoreCase = true) 
                }
            } else {
                emptyList()
            }
            
            updateState { 
                it.copy(
                    searchResults = mockResults,
                    isLoading = false
                ) 
            }
        }
    }

    private fun selectAsset(asset: Asset) {
        updateState { 
            it.copy(
                selectedAsset = asset,
                searchResults = emptyList(),
                searchQuery = asset.symbol
            ) 
        }
        validateForm()
    }

    private fun changeTransactionType(type: com.eliascoelho911.tickerpro.transaction.domain.model.TransactionType) {
        updateState { it.copy(transactionType = type) }
    }

    private fun changeQuantity(quantity: String) {
        updateState { it.copy(quantity = quantity) }
        validateForm()
    }

    private fun changePrice(price: String) {
        updateState { it.copy(price = price) }
        validateForm()
    }

    private fun changeDate(date: java.time.LocalDate) {
        updateState { it.copy(date = date) }
    }

    private fun changeFees(fees: String) {
        updateState { it.copy(fees = fees) }
        validateForm()
    }

    private fun changeNotes(notes: String) {
        updateState { it.copy(notes = notes) }
    }

    private fun validateForm() {
        val currentState = state.value
        val errors = mutableMapOf<String, String>()

        if (currentState.selectedAsset == null) {
            errors["asset"] = "Selecione um ativo"
        }

        if (currentState.quantity.isBlank()) {
            errors["quantity"] = "Quantidade é obrigatória"
        } else {
            try {
                val qty = BigDecimal(currentState.quantity)
                if (qty <= BigDecimal.ZERO) {
                    errors["quantity"] = "Quantidade deve ser maior que zero"
                }
            } catch (e: NumberFormatException) {
                errors["quantity"] = "Quantidade inválida"
            }
        }

        if (currentState.price.isBlank()) {
            errors["price"] = "Preço é obrigatório"
        } else {
            try {
                val price = BigDecimal(currentState.price)
                if (price <= BigDecimal.ZERO) {
                    errors["price"] = "Preço deve ser maior que zero"
                }
            } catch (e: NumberFormatException) {
                errors["price"] = "Preço inválido"
            }
        }

        if (currentState.fees.isNotBlank()) {
            try {
                val fees = BigDecimal(currentState.fees)
                if (fees < BigDecimal.ZERO) {
                    errors["fees"] = "Taxas não podem ser negativas"
                }
            } catch (e: NumberFormatException) {
                errors["fees"] = "Valor de taxas inválido"
            }
        }

        updateState { it.copy(errors = errors) }
    }

    private fun showConfirmation() {
        if (state.value.isFormValid) {
            updateState { it.copy(showConfirmation = true) }
        }
    }

    private fun hideConfirmation() {
        updateState { it.copy(showConfirmation = false) }
    }

    private fun saveTransaction() {
        val currentState = state.value
        
        if (!currentState.isFormValid) {
            viewModelScope.launch {
                sendAction(NewTransactionAction.ShowError("Formulário inválido"))
            }
            return
        }

        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val transaction = Transaction(
                    asset = currentState.selectedAsset!!,
                    type = currentState.transactionType,
                    quantity = currentState.quantityDecimal!!,
                    price = currentState.priceDecimal!!,
                    date = currentState.date,
                    fees = currentState.feesDecimal,
                    notes = currentState.notes.takeIf { it.isNotBlank() }
                )

                delay(1000)
                
                updateState { 
                    it.copy(
                        isLoading = false,
                        showConfirmation = false
                    ) 
                }
                
                sendAction(NewTransactionAction.TransactionSaved)
                
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false) }
                sendAction(NewTransactionAction.ShowError("Erro ao salvar transação: ${e.message}"))
            }
        }
    }

    private fun clearForm() {
        updateState { NewTransactionState() }
    }
}