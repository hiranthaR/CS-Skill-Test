package xyz.hirantha.csskilltest.data.remote.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.hirantha.csskilltest.data.remote.api.TypiCodeAPIService
import xyz.hirantha.csskilltest.internal.NoConnectivityException
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

class APIServiceDataSourceImpl(
    private val typiCodeAPIService: TypiCodeAPIService,
) :
    APIServiceDataSource {

    override val posts: LiveData<List<Post>>
        get() = _posts
    private val _posts = MutableLiveData<List<Post>>()

    override val users: LiveData<List<User>>
        get() = _users
    private val _users = MutableLiveData<List<User>>()

    override val post: LiveData<Post>
        get() = _post
    private val _post = MutableLiveData<Post>()

    override val user: LiveData<User>
        get() = _user
    private val _user = MutableLiveData<User>()

    override suspend fun getPosts() {
        try {
            val posts = typiCodeAPIService.getPosts().await()
            _posts.postValue(posts)
        } catch (e: NoConnectivityException) {
            Log.d("API Service", "no connectivity")
        } catch (e: Exception) {
            Log.d("API Service", e.message + "")
            e.printStackTrace()
        }
    }

    override suspend fun getUsers() {
        try {
            val users = typiCodeAPIService.getUsers().await()
            _users.postValue(users)
        } catch (e: NoConnectivityException) {
            Log.d("API Service", "no connectivity")
        } catch (e: Exception) {
            Log.d("API Service", e.message + "")
            e.printStackTrace()
        }
    }

    override suspend fun getPost(postId: Int) {
        try {
            val post = typiCodeAPIService.getPost(postId).await()
            _post.postValue(post)
        } catch (e: NoConnectivityException) {
            Log.d("API Service", "no connectivity")
        } catch (e: Exception) {
            Log.d("API Service", e.message + "")
            e.printStackTrace()
        }
    }

    override suspend fun getUser(userId: Int) {
        try {
            val user = typiCodeAPIService.getUser(userId).await()
            _user.postValue(user)
        } catch (e: NoConnectivityException) {
            Log.d("API Service", "no connectivity")
        } catch (e: Exception) {
            Log.d("API Service", e.message + "")
            e.printStackTrace()
        }
    }
}