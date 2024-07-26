package com.angellira.paris.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body  
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


private const val BASE_URL = "https://projeto-paris-default-rtdb.firebaseio.com/angel123456/jao123456/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface ParisApiService{
    @GET("users.json")
    suspend fun getUsers() :Map<String, User>

    @GET("users/{id}.json")
    suspend fun getUser(@Path("id") id: String) : User

    @POST("users.json")
    suspend fun saveUser(@Body user: User)
}

@Serializable
data class User(
    var name: String,
    var email: String,
    var password: String,
    val img: String
)

  object ParisApi{
      val retrofitService : ParisApiService by lazy{
          retrofit.create(ParisApiService::class.java)
      }
  }