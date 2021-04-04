package xyz.hirantha.csskilltest.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.factory
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.FragmentPostBinding
import xyz.hirantha.csskilltest.internal.ScopedFragment
import xyz.hirantha.csskilltest.models.Comment
import xyz.hirantha.csskilltest.models.PostAndUserWithComments
import xyz.hirantha.csskilltest.ui.listitems.CommentItem
import xyz.hirantha.csskilltest.ui.listitems.PostItem
import xyz.hirantha.csskilltest.ui.posts.PostsFragmentDirections


class PostFragment : ScopedFragment(), DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: ((Int) -> PostViewModelFactory) by factory()
    private lateinit var viewModel: PostViewModel
    private val args: PostFragmentArgs by navArgs()
    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory(args.postId)).get(PostViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.post.await().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.progressCircular.visibility = View.GONE
            binding.tvName.text = it.user.name
            binding.tvTitle.text = it.post.title
            binding.tvContent.text = it.post.body
            Glide.with(requireContext())
                .load(it.user.avatar)
                .centerCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(binding.imgAvatar)
            initCommentsRecyclerView(it.comments.toCommentItems())
        })
    }

    private fun List<Comment>.toCommentItems() = this.map { CommentItem(it) }

    private fun initCommentsRecyclerView(comments: List<CommentItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(comments)
        }
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }
}