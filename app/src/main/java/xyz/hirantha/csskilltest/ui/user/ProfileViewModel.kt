package xyz.hirantha.csskilltest.ui.user

import androidx.lifecycle.ViewModel
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.lazyDeferred

class ProfileViewModel(private val repository: Repository, private val userId: Int) : ViewModel() {
    val user by lazyDeferred { repository.getUser(userId) }
}