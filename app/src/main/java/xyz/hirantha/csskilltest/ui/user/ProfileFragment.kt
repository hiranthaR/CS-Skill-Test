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
import xyz.hirantha.csskilltest.BuildConfig
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.FragmentProfileBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment
import xyz.hirantha.csskilltest.models.User

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
            if (it == null) return@observeForever
            binding.progressCircular.visibility = View.GONE
            context?.let { context ->
                Glide.with(context)
                    .load(it.avatar)
                    .centerCrop()
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(binding.imgAvatar)
            }
            binding.tvName.text = it.name
            binding.tvEmail.text = it.email
            binding.tvAddress.text = "${it.address.street}, ${it.address.suite}, ${it.address.city}"
            binding.tvZipCode.text = it.address.zipcode
            binding.tvUsername.text = it.username
            binding.tvCompany.text = it.company.name
            binding.tvWebsite.text = it.website
            binding.tvPhone.text = it.phone

            context?.let { context ->
                Glide.with(context)
                    .load(getGoogleMapStaticImageUrl(it))
                    .centerCrop()
                    .placeholder(R.drawable.map)
                    .error(R.drawable.error)
                    .into(binding.imgMap)
            }
        }
    }

    private fun getGoogleMapStaticImageUrl(user: User) =
        "https://maps.googleapis.com/maps/api/staticmap?center=${user.address.geo.lat},${user.address.geo.lng}" +
                "&size=600x400&sensor=false&zoom=15&key=${BuildConfig.GOOGLE_MAP_API_KEY}"
}