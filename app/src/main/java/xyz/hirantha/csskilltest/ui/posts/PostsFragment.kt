package xyz.hirantha.csskilltest.ui.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.internal.ScopedFragment

class PostsFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: PostsViewModelFactory by instance()
    private lateinit var viewModel: PostsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.posts.await().observe(viewLifecycleOwner, {
            if (it == null) return@observe
        })
    }
}