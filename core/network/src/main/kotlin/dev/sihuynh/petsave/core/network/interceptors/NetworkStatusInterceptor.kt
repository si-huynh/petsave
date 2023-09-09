package dev.sihuynh.petsave.core.network.interceptors

import dev.sihuynh.petsave.core.common.network.utils.NetworkMonitor
import dev.sihuynh.petsave.core.logging.Logger
import dev.sihuynh.petsave.core.model.NetworkUnavailableException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(
    private val networkMonitor: NetworkMonitor,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Logger.d("Checking network...")
        val isOnline = runBlocking {
            networkMonitor.isOnline.first()
        }
        return if (isOnline) {
            Logger.d("Done. Proceed request.")
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}