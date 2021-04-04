package xyz.hirantha.csskilltest.data.providers

import androidx.lifecycle.LiveData

interface ThemeProvider {
    fun getTheme(): LiveData<Int>
    fun setTheme(theme:Int)
}