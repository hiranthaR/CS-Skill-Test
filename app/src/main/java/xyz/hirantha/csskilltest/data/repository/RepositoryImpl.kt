package xyz.hirantha.csskilltest.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.hirantha.csskilltest.data.db.dao.PostDao
import xyz.hirantha.csskilltest.data.db.dao.UserDao
import xyz.hirantha.csskilltest.data.remote.datasources.TypiCodeAPIServiceDataSource
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.PostAndUser
import xyz.hirantha.csskilltest.models.User

class RepositoryImpl(
    private val remoteDataSource: TypiCodeAPIServiceDataSource,
    private val postDao: PostDao,
    private val userDao: UserDao,
) : Repository {

    init {
        remoteDataSource.apply {
            posts.observeForever {
                persistPosts(it)
            }
            users.observeForever {
                persistUsers(it)
            }
        }
    }

    private fun persistUsers(users: List<User>?) {
        GlobalScope.launch(Dispatchers.IO) {
            users?.let { userDao.upsertUsers(it) }
        }
    }

    private fun persistPosts(posts: List<Post>?) {
        GlobalScope.launch(Dispatchers.IO) {
            posts?.let { postDao.upsertPosts(it) }
        }
    }

    override suspend fun getPosts(): LiveData<List<PostAndUser>> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getPosts()
            remoteDataSource.getUsers()
            return@withContext postDao.getPosts()
        }
    }
}