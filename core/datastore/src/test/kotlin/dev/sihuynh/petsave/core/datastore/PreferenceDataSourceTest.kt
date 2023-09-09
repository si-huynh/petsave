package dev.sihuynh.petsave.core.datastore

import dev.sihuynh.petsave.core.datastore.test.testUserPreferencesDataStore
import dev.sihuynh.petsave.core.model.user.DarkThemeConfig
import dev.sihuynh.petsave.core.model.user.ThemeBrand
import dev.sihuynh.petsave.core.model.user.Token
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class PreferenceDataSourceTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: PreferenceDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        subject = PreferenceDataSource(
            tmpFolder.testUserPreferencesDataStore(testScope)
        )
    }

    @Test
    fun shouldUseDynamicColorIsFalseByDefault() = testScope.runTest {
        assertFalse(subject.userData.first().useDynamicColor)
    }

    @Test
    fun userShouldUseDynamicColorIsTrueWhenSet() = testScope.runTest {
        subject.setDynamicColorPreference(true)
        assertTrue(subject.userData.first().useDynamicColor)
    }

    @Test
    fun shouldThemeBrandIsDefaultByDefault() = testScope.runTest {
        assertEquals(subject.userData.first().themeBrand, ThemeBrand.DEFAULT)
    }

    @Test
    fun shouldThemeBrandIsUpdatedWhenSet() = testScope.runTest {
        subject.setThemeBrand(ThemeBrand.ANDROID)
        assertEquals(subject.userData.first().themeBrand, ThemeBrand.ANDROID)
    }

    @Test
    fun shouldDarkThemeConfigIsFollowSystemByDefault() = testScope.runTest {
        assertEquals(subject.userData.first().darkThemeConfig, DarkThemeConfig.FOLLOW_SYSTEM)
    }

    @Test
    fun shouldDarkThemConfigIsUpdatedWhenSet() = testScope.runTest {
        subject.setDarkThemeConfig(DarkThemeConfig.DARK)
        assertEquals(subject.userData.first().darkThemeConfig, DarkThemeConfig.DARK)
    }

    @Test
    fun shouldTokeIsInvalidByDefault() = testScope.runTest {
        val token = subject.userData.first().token
        assertTrue(token.type.isEmpty())
        assertTrue(token.value.isEmpty())
        assertEquals(token.expiresIn, 0)
    }

    @Test
    fun shouldTokenIsValidWhenSet() = testScope.runTest {
        subject.setTokenInfo(Token(
            value = "1",
            type = "Bearer",
            expiresIn = 3600,
            expiresAt = Clock.System.now() + 3600.seconds,
        ))
        val token = subject.userData.first().token
        assertEquals(token.type, "Bearer")
        assertEquals(token.value, "1")
        assertEquals(token.expiresIn, 3600)
        assertTrue(token.expiresAt > Clock.System.now())
    }
}