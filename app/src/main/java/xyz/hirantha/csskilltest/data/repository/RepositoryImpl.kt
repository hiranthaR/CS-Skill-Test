package xyz.hirantha.csskilltest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.hirantha.csskilltest.data.db.dao.PostDao
import xyz.hirantha.csskilltest.data.db.dao.UserDao
import xyz.hirantha.csskilltest.data.remote.datasources.APIServiceDataSource
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.PostAndUser
import xyz.hirantha.csskilltest.models.User
import java.math.BigInteger
import java.security.MessageDigest

class RepositoryImpl(
    private val remoteDataSource: APIServiceDataSource,
    private val postDao: PostDao,
    private val userDao: UserDao,
) : Repository {

    init {
        remoteDataSource.apply {
            posts.observeForever {
                persistPosts(it)
            }
            users.observeForever {
                it.forEach { user ->
                    user.apply {
                        avatar = avatarUrl(user)
                    }
                }
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

    private fun avatarUrl(user: User) = "https://www.gravatar.com/avatar/${
        md5(
            user.email.trim().toLowerCase()
        )
    }?s=200"

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}