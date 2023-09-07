package dev.sihuynh.petsave.core.network.fake

import JvmUnitTestFakeAssetManager
import dev.sihuynh.petsave.core.common.network.Dispatcher
import dev.sihuynh.petsave.core.common.network.PetSaveDispatchers.IO
import dev.sihuynh.petsave.core.network.PetSaveNetworkDataSource
import dev.sihuynh.petsave.core.network.model.NetworkPaginatedAnimals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okio.use
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    @Suppress("VisibleForTests")
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager
) : PetSaveNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getNearByAnimals(
        pageToLoad: Int,
        pageSize: Int,
        postcode: String,
        maxDistance: Int
    ): NetworkPaginatedAnimals {
        return withContext(ioDispatcher) {
            assets.open("animals.json").use(networkJson::decodeFromStream)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getAnimalsByType(type: String): NetworkPaginatedAnimals {
        return withContext(ioDispatcher) {
            assets.open("animals.json").use(networkJson::decodeFromStream)
        }
    }
}