package com.example.marvel

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

//    https://gateway.marvel.com/v1/public/comics?apikey=d49e45249024d98b163e5c49e8268918&ts=1&hash=2df5c1901fb7fa0646fc5091731069ad&dateRange=2019-10-01,2019-10-02&format=comic

    @GET("v1/public/comics")
    fun getTop(
        @Query("limit") limit: Int,
        @Query("dateRange") dateRange: String //2019-10-01,2019-10-02
    ): Call<ComicResponse>


    @GET("v1/public/comics")
    fun getTopAfter(
        @Query("limit") limit: Int,
        @Query("dateRange") dateRange: String, //2019-10-01,2019-10-02
        @Query("offset") offset: Int
    ): Call<ComicResponse>


    @GET("v1/public/comics/{comicId}/characters")
    fun getComic(
//        @Query("limit") limit: Int,
//        @Query("offset") offset: Int,
        @Path("comicId") comicId: String
    ): Call<CharacterResponse>


    companion object {
        private const val BASE_URL = "https://gateway.marvel.com/"
        fun create(): RestApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })


            val authInterceptor = Interceptor { chain ->
                val newUrl = chain.request().url().newBuilder()
                    .addQueryParameter("apikey", "d49e45249024d98b163e5c49e8268918")
                    .addQueryParameter("ts", "1")//todo hashing md5
                    .addQueryParameter("hash", "2df5c1901fb7fa0646fc5091731069ad")//todo
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(authInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)
        }
    }
}

