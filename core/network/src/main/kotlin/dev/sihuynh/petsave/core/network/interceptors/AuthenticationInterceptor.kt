package dev.sihuynh.petsave.core.network.interceptors

import dev.sihuynh.petsave.core.datastore.PreferenceDataSource
import dev.sihuynh.petsave.core.logging.Logger
import dev.sihuynh.petsave.core.model.user.Token
import dev.sihuynh.petsave.core.network.BuildConfig
import dev.sihuynh.petsave.core.network.model.NetworkToken
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.AUTH_HEADER
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.TOKEN_TYPE
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.GRANT_TYPE_KEY
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.GRANT_TYPE_VALUE
import dev.sihuynh.petsave.core.network.retrofit.ApiConstants.AUTH_ENDPOINT
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.CLIENT_ID
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters.CLIENT_SECRET
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val dataStore: PreferenceDataSource
) : Interceptor {
    companion object {
        const val UNAUTHORIZED = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val userData = runBlocking {
            dataStore.userData.first()
        }
        val token = userData.token
        val tokenValue = token.value
        val request = chain.request()

        val interceptedRequest: Request

        if (token.expiresAt > Clock.System.now()) {
            interceptedRequest = chain.createAuthenticatedRequest(tokenValue)
        } else {
            val tokenRefreshResponse = chain.refreshToken()

            interceptedRequest = if (tokenRefreshResponse.isSuccessful) {
                val newToken = mapToken(tokenRefreshResponse)
                if (newToken.isValid()) {
                    runBlocking {
                        val saveToken = Token(
                            value = newToken.accessToken!!,
                            type = newToken.tokenType!!,
                            expiresIn = newToken.expiresInSeconds!!.toLong(),
                            expiresAt = Instant.fromEpochMilliseconds(newToken.expiresAt),
                        )
                        dataStore.setTokenInfo(saveToken)
                    }
                    chain.createAuthenticatedRequest(newToken.accessToken!!)
                } else {
                    request
                }
            } else {
                request
            }
        }

        return chain.proceedDeletingTokenIfUnAuthorized(interceptedRequest)
    }

    private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request =
        request().newBuilder().addHeader(AUTH_HEADER, TOKEN_TYPE + token).build()

    private fun Interceptor.Chain.refreshToken(): Response {
        val url = request()
            .url
            .newBuilder(AUTH_ENDPOINT)!!
            .build()

        val body = FormBody.Builder()
            .add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE)
            .add(CLIENT_ID, BuildConfig.CLIENT_ID)
            .add(CLIENT_SECRET, BuildConfig.CLIENT_SECRET)
            .build()

        val tokenRefresh = request()
            .newBuilder()
            .post(body)
            .url(url)
            .build()

        return proceedDeletingTokenIfUnAuthorized(tokenRefresh)
    }

    private fun Interceptor.Chain.proceedDeletingTokenIfUnAuthorized(request: Request): Response {
        val response = proceed(request)
        if (response.code == UNAUTHORIZED) {
            runBlocking {
                dataStore.setTokenInfo(
                    Token(
                        value = "",
                        expiresIn = -1,
                        expiresAt = Instant.DISTANT_PAST,
                        type = ""
                    )
                )
            }
        }
        return response
    }

    private fun mapToken(tokenRefreshResponse: Response): NetworkToken {
        val responseBody = tokenRefreshResponse.body!!
        return try {
            Json.decodeFromString(responseBody.string())
        } catch (e: Exception) {
            Logger.e("Failed to map token", e)
            NetworkToken.INVALID
        }
    }
}