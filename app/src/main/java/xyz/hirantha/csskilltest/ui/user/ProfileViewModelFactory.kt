package xyz.hirantha.csskilltest.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.hirantha.csskilltest.data.repository.Repository

class ProfileViewModelFactory(private val repository: Repository, private val userId: Int) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository, userId) as T
    }
}