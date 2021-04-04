package xyz.hirantha.csskilltest.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.FragmentSettingBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment

class SettingFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: SettingsViewModelFactory by instance()
    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: FragmentSettingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.theme.await().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.progressCircular.visibility = View.GONE
            binding.btgThemeGroup.visibility = View.VISIBLE
            val btnId = when (it) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> R.id.btn_system
                AppCompatDelegate.MODE_NIGHT_NO -> R.id.btn_light
                else -> R.id.btn_night
            }
            binding.btgThemeGroup.setSingleSelection(btnId)
            binding.btgThemeGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                val selectedTheme = when (checkedId) {
                    R.id.btn_system -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    R.id.btn_light -> AppCompatDelegate.MODE_NIGHT_NO
                    else -> AppCompatDelegate.MODE_NIGHT_YES
                }
                viewModel.setTheme(selectedTheme)
            }
        })
    }
}