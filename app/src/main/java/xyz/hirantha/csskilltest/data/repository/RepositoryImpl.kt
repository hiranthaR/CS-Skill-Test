package xyz.hirantha.csskilltest.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.hirantha.csskilltest.data.db.dao.PostDao
import xyz.hirantha.csskilltest.data.remote.datasources.TypiCodeAPIServiceDataSource
import xyz.hirantha.csskilltest.models.Post

class RepositoryImpl(
    private val remoteDataSource: TypiCodeAPIServiceDataSource,
    private val postDao: PostDao
) : Repository {

    init {
        remoteDataSource.apply {
            posts.observeForever {
                persistPosts(it)
            }
        }
    }

    private fun persistPosts(posts: List<Post>?) {
        GlobalScope.launch(Dispatchers.IO) {
            posts?.let { postDao.upsertPosts(it) }
        }
    }

    override suspend fun getPosts(): LiveData<List<Post>> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getPosts()
            return@withContext postDao.getPosts()
        }
    }
}