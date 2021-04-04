package xyz.hirantha.csskilltest.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.factory
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.internal.ScopedFragment


class PostFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: ((Int) -> PostViewModelFactory) by factory()
    private lateinit var viewModel: PostViewModel
    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory(args.postId)).get(PostViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.post.await().observe(viewLifecycleOwner,{
            if(it == null) return@observe
            Log.e("Post",it.toString())
        })
    }
}