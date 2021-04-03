package xyz.hirantha.csskilltest.data.remote.datasources

import androidx.lifecycle.LiveData
import xyz.hirantha.csskilltest.models.Post

interface TypiCodeAPIServiceDataSource {

    val posts: LiveData<List<Post>>

    suspend fun getPosts()
}