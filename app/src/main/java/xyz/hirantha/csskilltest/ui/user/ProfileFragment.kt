package xyz.hirantha.csskilltest.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.factory
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.FragmentProfileBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment

class ProfileFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: ((Int) -> ProfileViewModelFactory) by factory()
    private lateinit var viewModel: ProfileViewModel
    private val args: ProfileFragmentArgs by navArgs()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory(args.userId)).get(ProfileViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.user.await().observeForever {
            Glide.with(requireContext())
                .load(it.avatar)
                .centerCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(binding.imgAvatar)
            binding.tvName.text = it.name
            binding.tvEmail.text = it.email
            binding.tvAddress.text = "${it.address.street}, ${it.address.suite}, ${it.address.city}"
            binding.tvZipCode.text = it.address.zipcode
            binding.tvUsername.text = it.username
            binding.tvCompany.text = it.company.name
            binding.tvWebsite.text = it.website
            binding.tvPhone.text = it.phone
        }
    }
}