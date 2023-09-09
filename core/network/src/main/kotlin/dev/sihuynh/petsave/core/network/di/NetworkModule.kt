package dev.sihuynh.petsave.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sihuynh.petsave.core.common.network.utils.ConnectivityManagerNetworkMonitor
import dev.sihuynh.petsave.core.datastore.PreferenceDataSource
import dev.sihuynh.petsave.core.network.BuildConfig
import dev.sihuynh.petsave.core.network.interceptors.AuthenticationInterceptor
import dev.sihuynh.petsave.core.network.interceptors.LoggingInterceptor
import dev.sihuynh.petsave.core.network.interceptors.NetworkStatusInterceptor
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
        networkStatusInterceptor: NetworkStatusInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
    ): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(networkStatusInterceptor)
        .addInterceptor(authenticationInterceptor)
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
    ) = NetworkStatusInterceptor(networkMonitor)

    @Provides
    @Singleton
    fun providesAuthenticationInterceptor(
        dataSource: PreferenceDataSource
    ) = AuthenticationInterceptor(dataSource)
}