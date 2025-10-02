package com.droid.loginsignup.api

import com.droid.loginsignup.models.UnsplashPhoto
import com.droid.loginsignup.models.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiKeys {
    const val ACCESS_KEY = ApiUtilities.API_KEY
    const val SECRET_KEY = "your_secret_key_here"
}

interface UnsplashApi {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
        @Query("client_id") clientId: String = ApiKeys.ACCESS_KEY
    ): UnsplashResponse

    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("count") count: Int = 20,
        @Query("client_id") clientId: String = ApiKeys.ACCESS_KEY
    ): List<UnsplashPhoto>
}

object RetrofitInstance {
    val api: UnsplashApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApi::class.java)
    }
}
