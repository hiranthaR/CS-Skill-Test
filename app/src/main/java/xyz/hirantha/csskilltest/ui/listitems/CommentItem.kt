package xyz.hirantha.csskilltest.ui.listitems

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.databinding.CommentRowBinding
import xyz.hirantha.csskilltest.databinding.PostRowBinding
import xyz.hirantha.csskilltest.models.Comment
import xyz.hirantha.csskilltest.models.PostAndUser

class CommentItem(val comment:Comment): BindableItem<CommentRowBinding>() {
    override fun bind(viewBinding: CommentRowBinding, position: Int) {
        viewBinding.tvName.text = comment.name
        viewBinding.tvComment.text = comment.body
    }

    override fun getLayout(): Int = R.layout.comment_row

    override fun initializeViewBinding(view: View) = CommentRowBinding.bind(view)
}