package xyz.hirantha.csskilltest.data.repository

import androidx.lifecycle.LiveData
import xyz.hirantha.csskilltest.models.PostAndUser
import xyz.hirantha.csskilltest.models.PostAndUserWithComments
import xyz.hirantha.csskilltest.models.User

interface Repository {
    suspend fun getPosts(): LiveData<List<PostAndUser>>
    suspend fun getPost(postId:Int): LiveData<PostAndUserWithComments>
    suspend fun getUser(userId:Int): LiveData<User>
}