package dev.sihuynh.petsave.core.data.repository

import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.model.AnimalAggregate
import dev.sihuynh.petsave.core.database.model.OrganizationEntity
import dev.sihuynh.petsave.core.model.animal.Animal
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails
import dev.sihuynh.petsave.core.model.pagination.PaginatedAnimals
import dev.sihuynh.petsave.core.network.PetSaveNetworkDataSource
import dev.sihuynh.petsave.core.network.model.mappers.NetworkAnimalMapper
import dev.sihuynh.petsave.core.network.model.mappers.NetworkPaginationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetFinderAnimalRepository @Inject constructor(
    private val animalsDao: AnimalsDao,
    private val organizationsDao: OrganizationsDao,
    private val network: PetSaveNetworkDataSource,
    private val animalMapper: NetworkAnimalMapper,
    private val paginationMapper: NetworkPaginationMapper,
): AnimalRepository {

    override fun getAnimals(): Flow<List<Animal>> {
        return animalsDao.getAllAnimals().map { animalList ->
            animalList.map { animalAggregate ->
                animalAggregate.animal.toAnimalDomain(
                    photos = animalAggregate.photos,
                    videos = animalAggregate.videos,
                    tags = animalAggregate.tags
                )
            }
        }
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