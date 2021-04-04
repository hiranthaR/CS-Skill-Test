package xyz.hirantha.csskilltest.ui.createpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.hirantha.csskilltest.data.repository.Repository

class CreatePostViewModelFactory(private val repository: Repository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreatePostViewModel(repository) as T
    }
}