package xyz.hirantha.csskilltest.data.remote.datasources

import androidx.lifecycle.LiveData
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

interface APIServiceDataSource {

    val posts: LiveData<List<Post>>
    val users: LiveData<List<User>>
    val post: LiveData<Post>
    val user: LiveData<User>

    suspend fun getPosts()
    suspend fun getUsers()
    suspend fun getPost(postId:Int)
    suspend fun getUser(userId:Int)
}