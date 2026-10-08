// app/src/main/java/project/background/ApiService.kt
package project.background

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("posts/1")
    suspend fun getData(): retrofit2.Response<DataResponse>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

data class DataResponse(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)