package xyz.hirantha.csskilltest.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.hirantha.csskilltest.data.db.dao.CommentDao
import xyz.hirantha.csskilltest.data.db.dao.PostDao
import xyz.hirantha.csskilltest.data.db.dao.UserDao
import xyz.hirantha.csskilltest.data.remote.datasources.APIServiceDataSource
import xyz.hirantha.csskilltest.internal.Response
import xyz.hirantha.csskilltest.models.*
import java.math.BigInteger
import java.security.MessageDigest

class RepositoryImpl(
    private val remoteDataSource: APIServiceDataSource,
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val commentDao: CommentDao,
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

            post.observeForever {
                persistPost(it)
            }

            user.observeForever {
                it.apply {
                    avatar = avatarUrl(it)
                }
                persistUser(it)
            }

            comments.observeForever { persistComments(it) }
        }
    }

    private fun persistComments(comments: List<Comment>?) {
        GlobalScope.launch(Dispatchers.IO) {
            comments?.let { commentDao.upsertComments(it) }
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

    private fun persistUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.upsertUser(user)
        }
    }

    private fun persistPost(post: Post) {
        GlobalScope.launch(Dispatchers.IO) {
            postDao.upsertPost(post)
        }
    }

    override suspend fun getPosts(): LiveData<List<PostAndUser>> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getPosts()
            remoteDataSource.getUsers()
            return@withContext postDao.getPosts()
        }
    }

    override suspend fun getPost(postId: Int): LiveData<PostAndUserWithComments> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getPost(postId)
            remoteDataSource.getComments(postId)
            return@withContext postDao.getPost(postId)
        }
    }

    override suspend fun getUser(userId: Int): LiveData<User> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getUser(userId)
            return@withContext userDao.getUser(userId)
        }
    }

    override suspend fun addPost(title: String, body: String): Response {
        return withContext(Dispatchers.IO) {
            return@withContext remoteDataSource.addPost(
                title,
                body
            )
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