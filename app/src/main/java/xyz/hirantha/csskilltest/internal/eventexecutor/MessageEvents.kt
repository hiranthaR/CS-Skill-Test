package xyz.hirantha.csskilltest.internal.eventexecutor

import android.content.Intent

interface MessageEvents {
    fun navigateUp(): Boolean
    fun navigate(action: Int)
    fun showSnackBar(s: String)
}