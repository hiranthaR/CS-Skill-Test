package xyz.hirantha.csskilltest.ui.settings

import androidx.lifecycle.ViewModel
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.internal.lazyDeferred

class SettingViewModel(private val repository: Repository) : ViewModel() {

    val theme by lazyDeferred { repository.getTheme() }

    fun setTheme(selectedTheme: Int) = repository.setTheme(selectedTheme)
}