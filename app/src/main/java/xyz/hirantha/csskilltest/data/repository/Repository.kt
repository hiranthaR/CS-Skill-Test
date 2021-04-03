package xyz.hirantha.csskilltest.data.repository

import androidx.lifecycle.LiveData
import xyz.hirantha.csskilltest.models.Post

interface Repository {
    suspend fun getPosts(): LiveData<List<Post>>
}