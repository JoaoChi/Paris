package com.angellira.paris.network

import com.angellira.paris.model.SportPhoto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


private const val BASE_URL = "https://projeto-paris-default-rtdb.firebaseio.com/angel123456/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface MarsApiService{
    @GET("sports.json")
    suspend fun getPhotos() : Map<String, SportPhoto>
}

  object MarsApi{
      val retrofitService : MarsApiService by lazy{
          retrofit.create(MarsApiService::class.java)
      }
  }