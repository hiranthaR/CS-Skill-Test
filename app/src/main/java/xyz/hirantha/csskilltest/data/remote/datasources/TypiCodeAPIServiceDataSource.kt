package xyz.hirantha.csskilltest.data.remote.datasources

import androidx.lifecycle.LiveData
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

interface TypiCodeAPIServiceDataSource {

    val posts: LiveData<List<Post>>
    val users: LiveData<List<User>>

    suspend fun getPosts()
    suspend fun getUsers()
}