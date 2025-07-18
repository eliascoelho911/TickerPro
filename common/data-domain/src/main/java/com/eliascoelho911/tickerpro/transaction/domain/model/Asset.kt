package com.eliascoelho911.tickerpro.transaction.domain.model

data class Asset(
    val id: String,
    val symbol: String,
    val name: String,
    val type: AssetType = AssetType.STOCK
)

enum class AssetType {
    STOCK,
    ETF,
    CRYPTO,
    FUND
}