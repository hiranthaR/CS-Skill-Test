package xyz.hirantha.csskilltest.data.providers

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val THEME = "pref:theme"

class ThemeProviderImpl(context: Context) : PreferenceProvider(context), ThemeProvider {

    private val _theme = MutableLiveData(readTheme())

    override fun getTheme(): LiveData<Int> {
        return _theme
    }

    override fun setTheme(theme: Int) {
        preference.edit()
            .putInt(THEME, theme)
            .apply()
        _theme.postValue(theme)
    }

    private fun readTheme() = preference.getInt(THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}