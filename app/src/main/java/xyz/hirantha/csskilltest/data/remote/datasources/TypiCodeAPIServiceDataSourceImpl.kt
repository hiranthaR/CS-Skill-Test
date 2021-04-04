package xyz.hirantha.csskilltest.data.remote.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.hirantha.csskilltest.data.remote.api.TypiCodeAPIService
import xyz.hirantha.csskilltest.internal.NoConnectivityException
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

class TypiCodeAPIServiceDataSourceImpl(private val apiService: TypiCodeAPIService) :
    TypiCodeAPIServiceDataSource {

    override val posts: LiveData<List<Post>>
        get() = _posts
    private val _posts = MutableLiveData<List<Post>>()

    override val users: LiveData<List<User>>
        get() = _users
    private val _users = MutableLiveData<List<User>>()

    override suspend fun getPosts() {
        try {
            val posts = apiService.getPosts().await()
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
            val users = apiService.getUsers().await()
            _users.postValue(users)
        } catch (e: NoConnectivityException) {
            Log.d("API Service", "no connectivity")
        } catch (e: Exception) {
            Log.d("API Service", e.message + "")
            e.printStackTrace()
        }
    }
}