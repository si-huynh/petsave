package dev.sihuynh.petsave.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.sihuynh.petsave.core.data.mediator.AnimalRemoteMediator
import dev.sihuynh.petsave.core.database.PetSaveDatabase
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.daos.RemoteKeysDao
import dev.sihuynh.petsave.core.database.model.AnimalAggregate
import dev.sihuynh.petsave.core.database.model.OrganizationEntity
import dev.sihuynh.petsave.core.model.animal.Animal
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails
import dev.sihuynh.petsave.core.model.pagination.PaginatedAnimals
import dev.sihuynh.petsave.core.model.pagination.Pagination
import dev.sihuynh.petsave.core.network.PetSaveNetworkDataSource
import dev.sihuynh.petsave.core.network.model.mappers.NetworkAnimalMapper
import dev.sihuynh.petsave.core.network.model.mappers.NetworkPaginationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetFinderAnimalRepository @Inject constructor(
    private val database: PetSaveDatabase,
    private val animalsDao: AnimalsDao,
    private val organizationsDao: OrganizationsDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val network: PetSaveNetworkDataSource,
    private val animalMapper: NetworkAnimalMapper,
    private val paginationMapper: NetworkPaginationMapper,
): AnimalRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimalStream(): Flow<PagingData<AnimalAggregate>> {
        val pagingSourceFactory = { animalsDao.getPaginatedAnimals() }
        return Pager(
            config = PagingConfig(
                pageSize = Pagination.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = AnimalRemoteMediator(
                database = database,
                network = network,
                animalsDao = animalsDao,
                animalMapper = animalMapper,
                remoteKeysDao = remoteKeysDao,
                organizationsDao = organizationsDao,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun getAnimals(): Flow<List<Animal>> =
        animalsDao.getAllAnimals().map { animals ->
            animals.map { it.animal.toAnimalDomain(it.photos, it.videos, it.tags) }
        }

    override suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals {
        val (networkAnimals, apiPagination) = network.getNearByAnimals(
            pageToLoad = pageToLoad,
            pageSize = numberOfItems,
            postcode = "07097",
            maxDistance = 100
        )

        return PaginatedAnimals(
            animals = networkAnimals?.map {
                animalMapper.mapToDomain(it)
            }.orEmpty(),
            pagination = paginationMapper.mapToDomain(apiPagination)
        )
    }

    override suspend fun storeAnimals(animals: List<AnimalWithDetails>) {
        val organizations = animals.map { OrganizationEntity.fromDomain(it.details.organization) }
        organizationsDao.insert(organizations)
        animalsDao.insertAnimalsWithDetails(
            animals.map { AnimalAggregate.fromDomain(it) }
        )
    }
}