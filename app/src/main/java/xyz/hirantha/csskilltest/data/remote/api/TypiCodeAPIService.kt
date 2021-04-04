package xyz.hirantha.csskilltest.data.remote.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import xyz.hirantha.csskilltest.data.remote.interceptors.ConnectivityInterceptor
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

interface TypiCodeAPIService {

    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>

    @GET("/users")
    fun getUsers(): Deferred<List<User>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") postId:Int): Deferred<Post>

    @GET("/users/{id}")
    fun getUser(@Path("id") userId:Int): Deferred<User>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TypiCodeAPIService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypiCodeAPIService::class.java)
        }
    }
}