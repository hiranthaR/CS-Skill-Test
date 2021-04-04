package xyz.hirantha.csskilltest.ui.createpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.eventexecutor.LiveMessageEvent
import xyz.hirantha.csskilltest.internal.eventexecutor.MessageEvents

class CreatePostViewModel(private val repository: Repository) : ViewModel() {
    private val _posting = MutableLiveData<Boolean>(false)
    val posting:LiveData<Boolean> get() = _posting

    //for send events to fragment
    val liveMessageEvent = LiveMessageEvent<MessageEvents>()


}