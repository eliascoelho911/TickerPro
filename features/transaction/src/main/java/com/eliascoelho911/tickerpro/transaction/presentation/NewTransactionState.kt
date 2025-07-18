package com.eliascoelho911.tickerpro.transaction.presentation

import androidx.compose.runtime.Immutable
import com.eliascoelho911.tickerpro.core.arch.UIState
import com.eliascoelho911.tickerpro.transaction.domain.model.Asset
import com.eliascoelho911.tickerpro.transaction.domain.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDate

@Immutable
data class NewTransactionState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Asset> = emptyList(),
    val selectedAsset: Asset? = null,
    val transactionType: TransactionType = TransactionType.BUY,
    val quantity: String = "",
    val price: String = "",
    val date: LocalDate = LocalDate.now(),
    val fees: String = "0",
    val notes: String = "",
    val errors: Map<String, String> = emptyMap(),
    val showConfirmation: Boolean = false
) : UIState {
    
    val isFormValid: Boolean
        get() = selectedAsset != null &&
                quantity.isNotBlank() &&
                price.isNotBlank() &&
                errors.isEmpty()
    
    val quantityDecimal: BigDecimal?
        get() = try { 
            if (quantity.isBlank()) null else BigDecimal(quantity) 
        } catch (e: NumberFormatException) { 
            null 
        }
    
    val priceDecimal: BigDecimal?
        get() = try { 
            if (price.isBlank()) null else BigDecimal(price) 
        } catch (e: NumberFormatException) { 
            null 
        }
    
    val feesDecimal: BigDecimal
        get() = try { 
            if (fees.isBlank()) BigDecimal.ZERO else BigDecimal(fees) 
        } catch (e: NumberFormatException) { 
            BigDecimal.ZERO 
        }
    
    val totalValue: BigDecimal?
        get() = quantityDecimal?.let { qty ->
            priceDecimal?.let { prc ->
                qty.multiply(prc)
            }
        }
    
    val totalCost: BigDecimal?
        get() = totalValue?.add(feesDecimal)
}