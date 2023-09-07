package dev.sihuynh.petsave.core.network.fake

import JvmUnitTestFakeAssetManager
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeNetworkDataSourceTest {

    private lateinit var subject: FakeNetworkDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        subject = FakeNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager
        )
    }

    @Test
    fun testDeserializationOfAnimals() = runTest(testDispatcher) {
        assertEquals(
            mockAnimal,
            subject.getAnimalsByType("dog").animals?.first()
        )
    }
}

