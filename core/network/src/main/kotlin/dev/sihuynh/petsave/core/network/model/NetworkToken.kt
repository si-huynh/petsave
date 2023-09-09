package dev.sihuynh.petsave.core.network.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Duration.Companion.seconds

@Serializable
data class NetworkToken(
    @SerialName("token_type") val tokenType: String?,
    @SerialName("expires_in") val expiresInSeconds: Int?,
    @SerialName("access_token") val accessToken: String?
) {
    companion object {
        val INVALID = NetworkToken("", -1, "")
    }

    @Transient
    private val requestAt: Instant = Clock.System.now()

    val expiresAt: Long
        get() {
            if (expiresInSeconds == null) return 0L
            return requestAt.plus(expiresInSeconds.seconds).toEpochMilliseconds()
        }

    fun isValid(): Boolean {
        return !tokenType.isNullOrEmpty() &&
                !accessToken.isNullOrEmpty() &&
                expiresInSeconds != null && expiresInSeconds >= 0
    }
}
