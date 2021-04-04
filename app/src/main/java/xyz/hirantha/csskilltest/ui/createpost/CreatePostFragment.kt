package xyz.hirantha.csskilltest.ui.createpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.internal.ScopedFragment
import xyz.hirantha.csskilltest.internal.eventexecutor.MessageEvents

class CreatePostFragment : ScopedFragment(),MessageEvents {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }
}