package com.hamcoding.berryy.data.source.remote

import com.hamcoding.berryy.BuildConfig
import com.hamcoding.berryy.data.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface KrxApiClient {
    @GET("getItemInfo")
    suspend fun getStockList(
        @Query("likeItmsNm") name: String,
        @Query("serviceKey") apiKey: String = BuildConfig.KRX_API_KEY,
        @Query("resultType") resultType: String = "json"
    ): SearchResponse

    companion object {

        private const val BASE_URL =
            "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/"

        fun create(): KrxApiClient {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KrxApiClient::class.java)

        }
    }
}