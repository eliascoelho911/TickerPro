package com.eliascoelho911.tickerpro.transaction.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Transaction(
    val id: String? = null,
    val asset: Asset,
    val type: TransactionType,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val date: LocalDate,
    val fees: BigDecimal = BigDecimal.ZERO,
    val notes: String? = null
) {
    val totalValue: BigDecimal
        get() = price.multiply(quantity)
    
    val totalCost: BigDecimal
        get() = totalValue.add(fees)
}