package xyz.hirantha.csskilltest.ui.listitems

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.PostRowBinding
import xyz.hirantha.csskilltest.models.PostAndUser

class PostItem(private val postAndUser: PostAndUser,private val context: Context): BindableItem<PostRowBinding>() {
    override fun bind(viewBinding: PostRowBinding, position: Int) {
        viewBinding.tvName.text = postAndUser.user.name
        viewBinding.tvContent.text = postAndUser.post.body
        viewBinding.tvPost.text = postAndUser.post.title

        Glide.with(context)
            .load(postAndUser.user.avatar)
            .centerCrop()
            .placeholder(R.drawable.user)
            .error(R.drawable.user)
            .into(viewBinding.imgProfile)
    }

    override fun getLayout(): Int = R.layout.post_row

    override fun initializeViewBinding(view: View) = PostRowBinding.bind(view)
}