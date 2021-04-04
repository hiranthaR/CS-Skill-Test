package xyz.hirantha.csskilltest.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.hirantha.csskilltest.data.repository.Repository

class PostViewModelFactory(private val repository: Repository, private val postId:Int):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository,postId) as T
    }
}