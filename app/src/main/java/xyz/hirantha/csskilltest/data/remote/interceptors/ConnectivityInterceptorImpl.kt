package xyz.hirantha.csskilltest.data.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import xyz.hirantha.csskilltest.internal.NoConnectivityException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("url",chain.request().url.toString())
        if (!isOnline()) throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManger =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManger.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}