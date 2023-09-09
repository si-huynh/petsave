package dev.sihuynh.petsave.core.network.interceptors

import dev.sihuynh.petsave.core.common.network.di.ApplicationScope
import dev.sihuynh.petsave.core.common.network.utils.NetworkMonitor
import dev.sihuynh.petsave.core.logging.Logger
import dev.sihuynh.petsave.core.model.NetworkUnavailableException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class NetworkStatusInterceptor @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    @ApplicationScope private val scope: CoroutineScope,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Logger.d("Checking network...")
        val isOffline = networkMonitor.isOnline
            .map(Boolean::not)
            .stateIn(
                scope,
                SharingStarted.WhileSubscribed(0),
                initialValue = false
            ).value
        return if (isOffline) {
            throw NetworkUnavailableException()
        } else {
            Logger.d("Done. Proceed request.")
            chain.proceed(chain.request())
        }
    }
}