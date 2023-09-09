package dev.sihuynh.petsave.core.network.interceptors

import com.google.common.truth.Truth.assertThat
import dev.sihuynh.petsave.core.datastore.PreferenceDataSource
import dev.sihuynh.petsave.core.datastore.test.testUserPreferencesDataStore
import dev.sihuynh.petsave.core.model.user.Token
import dev.sihuynh.petsave.core.network.retrofit.ApiConstants
import dev.sihuynh.petsave.core.network.retrofit.ApiParameters
import dev.sihuynh.petsave.core.network.utils.JsonReader
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

@RunWith(RobolectricTestRunner::class)
class AuthenticationInterceptorTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var preferenceDataSource: PreferenceDataSource
    private lateinit var mockWebServer: MockWebServer
    private lateinit var authenticationInterceptor: AuthenticationInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val endpointSeparator = "/"
    private val animalsEndpointPath = endpointSeparator + ApiConstants.ANIMALS_ENDPOINT
    private val authEndpointPath = endpointSeparator + ApiConstants.AUTH_ENDPOINT
    private val validToken = "validToken"
    private val expiredToken = "expiredToken"

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        preferenceDataSource = PreferenceDataSource(
            tmpFolder.testUserPreferencesDataStore(testScope)
        )

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        authenticationInterceptor = AuthenticationInterceptor(preferenceDataSource)
        okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun authenticationInterceptor_validToken() = testScope.runTest {
        // Given
        preferenceDataSource.setTokenInfo(
            Token(
                value = validToken,
                expiresIn = 3600,
                expiresAt = Clock.System.now() + 3600.seconds,
                type = "Bearer"
            )
        )
        mockWebServer.dispatcher = getDispatcherForValidToken()

        // When
        okHttpClient.newCall(
            Request.Builder()
                .url(mockWebServer.url(ApiConstants.ANIMALS_ENDPOINT))
                .build()
        ).execute()

        // Then
        val request = mockWebServer.takeRequest()

        with(request) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(animalsEndpointPath)
            assertThat(getHeader(ApiParameters.AUTH_HEADER))
                .isEqualTo(ApiParameters.TOKEN_TYPE + validToken)
        }
    }

    @Test
    fun authenticationInterceptor_expiredToken() = testScope.runTest {
        // Given
        preferenceDataSource.setTokenInfo(
            Token(
                value = expiredToken,
                expiresIn = -1,
                expiresAt = Instant.DISTANT_PAST,
                type = ""
            )
        )
        mockWebServer.dispatcher = getDispatcherForExpiredToken()

        // When
        okHttpClient.newCall(
            Request.Builder()
                .url(mockWebServer.url(ApiConstants.ANIMALS_ENDPOINT))
                .build()
        ).execute()

        // Then
        val tokenRequest = mockWebServer.takeRequest()
        val animalsRequest = mockWebServer.takeRequest()

        with(tokenRequest) {
            assertThat(method).isEqualTo("POST")
            assertThat(path).isEqualTo(authEndpointPath)
        }

        val token = preferenceDataSource.userData.first().token
        assertEquals(token.value, validToken)
        assertEquals(token.type, "Bearer")
        assertEquals(token.expiresIn, 3600)
        assertTrue(token.expiresAt > Clock.System.now())

        with(animalsRequest) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(animalsEndpointPath)
        }
    }

    private fun getDispatcherForValidToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                animalsEndpointPath -> {
                    MockResponse().setResponseCode(200)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun getDispatcherForExpiredToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                authEndpointPath -> {
                    MockResponse().setResponseCode(200).setBody(
                        JsonReader.getJson("networkResponses/validToken.json")
                    )
                }
                animalsEndpointPath -> {
                    MockResponse().setResponseCode(200)
                }

                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }
}