package com.nguyenminhkhang.moneymind.data.remote

import com.nguyenminhkhang.moneymind.data.model.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("v6/{apiKey}/latest/{base}")
    suspend fun getRates(
        @Path("apiKey") apiKey: String,
        @Path("base") base: String
    ): ExchangeRateResponse
}