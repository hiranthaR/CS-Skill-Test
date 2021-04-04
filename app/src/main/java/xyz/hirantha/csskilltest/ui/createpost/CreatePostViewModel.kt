package xyz.hirantha.csskilltest.ui.createpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.Response
import xyz.hirantha.csskilltest.internal.eventexecutor.LiveMessageEvent
import xyz.hirantha.csskilltest.internal.eventexecutor.MessageEvents

class CreatePostViewModel(private val repository: Repository) : ViewModel() {
    private val _posting = MutableLiveData<Boolean>(false)
    val posting: LiveData<Boolean> get() = _posting

    //for send events to fragment
    val liveMessageEvent = LiveMessageEvent<MessageEvents>()

    fun createPost(title: String, content: String) {
        _posting.postValue(true)
        GlobalScope.launch(Dispatchers.IO) {
            when (repository.addPost(title, content)) {
                Response.DONE -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        liveMessageEvent.sendEvent { showSnackBar("Post added successfully!") }
                    }
                }
                Response.CONNECTION_FAIL -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        liveMessageEvent.sendEvent { showSnackBar("No Collection!") }
                    }
                }
                Response.ERROR -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        liveMessageEvent.sendEvent { showSnackBar("Something went wrong!") }
                    }
                }
            }
        }
    }
}