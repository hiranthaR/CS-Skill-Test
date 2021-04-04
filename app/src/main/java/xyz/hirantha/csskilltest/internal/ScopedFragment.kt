package xyz.hirantha.csskilltest.internal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class ScopedFragment : Fragment(), CoroutineScope {
    private lateinit var job: Job
    lateinit var navController: NavController

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun navigateUp() = navController.navigateUp()

    fun navigate(action: Int) {
        navController.navigate(action)
    }

    fun showSnackBar(s: String) {
        if (view != null) Snackbar.make(requireView(), s, Snackbar.LENGTH_SHORT).show()
    }
}