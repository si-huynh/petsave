package dev.sihuynh.petsave.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sihuynh.petsave.core.common.network.di.ApplicationScope
import dev.sihuynh.petsave.core.common.network.utils.ConnectivityManagerNetworkMonitor
import dev.sihuynh.petsave.core.common.network.utils.NetworkMonitor
import dev.sihuynh.petsave.core.network.BuildConfig
import dev.sihuynh.petsave.core.network.interceptors.LoggingInterceptor
import dev.sihuynh.petsave.core.network.interceptors.NetworkStatusInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(
        loggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor
    ): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(networkStatusInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor) =
        HttpLoggingInterceptor(loggingInterceptor).apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun providesNetworkStatusInterceptor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
        @ApplicationScope scope: CoroutineScope,
    ) = NetworkStatusInterceptor(networkMonitor, scope)
}