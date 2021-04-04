package xyz.hirantha.csskilltest.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import xyz.hirantha.csskilltest.R
import xyz.hirantha.csskilltest.internal.ScopedFragment


class PostFragment : ScopedFragment(),DIAware {

    override val di: DI by closestDI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }
}