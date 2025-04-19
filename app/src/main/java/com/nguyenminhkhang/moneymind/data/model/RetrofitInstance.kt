package com.nguyenminhkhang.moneymind.data.model

import com.nguyenminhkhang.moneymind.data.remote.ExchangeRateApi

object RetrofitInstance {
    private const val BASE_URL = "https://v6.exchangerate-api.com/"

    val api: ExchangeRateApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }
    }