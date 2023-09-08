package dev.sihuynh.petsave.core.datastore

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class UserPreferencesSerializerTest {
    private val userPreferencesSerializer = UserPreferencesSerializer()

    @Test
    fun defaultUserPreferences_isEmpty() {
        assertEquals(
            userPreferences {  },
            userPreferencesSerializer.defaultValue
        )
    }

    @Test
    fun writingAndReadingUserPreferences_outputsCorrectValue() = runTest {
        val expectedUserPreferences = userPreferences {
            useDynamicColor = true
            darkThemeConfig = DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
            themeBrand = ThemeBrandProto.THEME_BRAND_ANDROID
            token = "1"
            tokenType = "Bearer"
            expiresIn = 3600
        }

        val outputStream = ByteArrayOutputStream()
        expectedUserPreferences.writeTo(outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val actualUserPreferences = userPreferencesSerializer.readFrom(inputStream)

        assertEquals(expectedUserPreferences, actualUserPreferences)
    }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
        val inputStream = ByteArrayInputStream(byteArrayOf(1))
        userPreferencesSerializer.readFrom(inputStream)
    }
}