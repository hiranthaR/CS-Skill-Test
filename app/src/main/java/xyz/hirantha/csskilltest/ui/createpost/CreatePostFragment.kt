package xyz.hirantha.csskilltest.ui.createpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import xyz.hirantha.csskilltest.databinding.FragmentCreatePostBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment
import xyz.hirantha.csskilltest.internal.eventexecutor.MessageEvents

class CreatePostFragment : ScopedFragment(), DIAware, MessageEvents {

    override val di: DI by closestDI()
    private lateinit var viewModel: CreatePostViewModel
    private val viewModelFactory: CreatePostViewModelFactory by instance()
    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CreatePostViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        viewModel.liveMessageEvent.setEventReceiver(
            this@CreatePostFragment,
            this@CreatePostFragment
        )

        binding.btnCreatePost.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            if (title.isEmpty()) {
                return@setOnClickListener showSnackBar("Title must be provided!")
            }

            if (content.isEmpty()) {
                return@setOnClickListener showSnackBar("Content must be provided!")
            }

            viewModel.createPost(title, content)
        }

        viewModel.posting.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.btnCreatePost.isEnabled = !it
        })
    }
}