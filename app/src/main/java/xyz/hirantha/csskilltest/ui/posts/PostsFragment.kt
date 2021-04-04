package xyz.hirantha.csskilltest.ui.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.FragmentPostsBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment
import xyz.hirantha.csskilltest.models.PostAndUser
import xyz.hirantha.csskilltest.ui.listitems.PostItem

class PostsFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: PostsViewModelFactory by instance()
    private lateinit var viewModel: PostsViewModel
    private lateinit var binding: FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        binding.fabToggleThem.setOnClickListener {
            viewModel.toggleTheme()
        }

        binding.fabAddPost.setOnClickListener {
            navController.navigate(R.id.action_postsFragment_to_createPostFragment)
        }

        viewModel.posts.await().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.progressCircular.visibility = View.GONE
            initPostRecyclerView(it.toPostItems())
        })
    }

    private fun List<PostAndUser>.toPostItems(): List<PostItem> =
        this.map { PostItem(it, requireContext(), navController) }

    private fun initPostRecyclerView(posts: List<PostItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(posts)
            setOnItemClickListener { item, _ ->
                (item as? PostItem)?.let { postItem ->
                    val action =
                        PostsFragmentDirections.actionPostsFragmentToPostFragment(postItem.postAndUser.post.id)
                    navController.navigate(action)
                }
            }
        }
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }
}