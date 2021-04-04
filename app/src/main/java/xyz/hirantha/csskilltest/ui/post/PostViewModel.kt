package xyz.hirantha.csskilltest.ui.post

import androidx.lifecycle.ViewModel
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.lazyDeferred

class PostViewModel(private val repository: Repository,private val postId:Int):ViewModel() {
    val post by lazyDeferred { repository.getPost(postId) }
}