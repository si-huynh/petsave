package dev.sihuynh.petsave.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.sihuynh.petsave.core.database.PetSaveDatabase
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.daos.RemoteKeysDao
import dev.sihuynh.petsave.core.network.fake.FakeAssetManager
import dev.sihuynh.petsave.core.network.fake.FakeNetworkDataSource
import dev.sihuynh.petsave.core.network.model.mappers.NetworkAnimalMapper
import dev.sihuynh.petsave.core.network.model.mappers.NetworkPaginationMapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStream
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class PetFinderAnimalRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: AnimalRepository

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var networkJson: Json

    @Inject
    lateinit var database: PetSaveDatabase

    @Inject
    lateinit var animalsDao: AnimalsDao

    @Inject
    lateinit var organizationsDao: OrganizationsDao

    @Inject
    lateinit var remoteKeysDao: RemoteKeysDao

    @Inject
    lateinit var animalMapper: NetworkAnimalMapper

    @Inject
    lateinit var paginationMapper: NetworkPaginationMapper

    @Before
    fun setup() {
        hiltRule.inject()

        val fakeNetworkDataSource = FakeNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = networkJson,
            assets = PetFinderFakeAssetManager
        )

        repository = PetFinderAnimalRepository(
            database = database,
            animalsDao = animalsDao,
            organizationsDao = organizationsDao,
            remoteKeysDao = remoteKeysDao,
            network = fakeNetworkDataSource,
            animalMapper = animalMapper,
            paginationMapper = paginationMapper
        )
    }

    @Test
    fun requestMoreAnimals_success() = runTest(testDispatcher) {
        // Given
        val expectedTotalItems = 20

        // When
        val paginatedAnimals = repository.requestMoreAnimals(
            pageToLoad = 1,
            numberOfItems = 20,
        )

        // Then
        val animals = paginatedAnimals.animals
        assertEquals(animals.size, expectedTotalItems)
    }
}

object PetFinderFakeAssetManager : FakeAssetManager {
    override fun open(fileName: String): InputStream {
        print(fileName)
        return InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.assets.open(fileName)
    }
}