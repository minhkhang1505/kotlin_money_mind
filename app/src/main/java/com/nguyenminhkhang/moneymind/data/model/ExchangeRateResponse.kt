package com.nguyenminhkhang.moneymind.data.model

data class ExchangeRateResponse(
    val result: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)
