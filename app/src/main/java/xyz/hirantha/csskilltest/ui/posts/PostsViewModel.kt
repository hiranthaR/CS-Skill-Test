package xyz.hirantha.csskilltest.ui.posts

import androidx.lifecycle.ViewModel
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.lazyDeferred

class PostsViewModel(private val repository: Repository) : ViewModel() {
    val posts by lazyDeferred { repository.getPosts() }
    val theme by lazyDeferred { repository.getTheme() }
}