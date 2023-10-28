package dev.sihuynh.petsave.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.sihuynh.petsave.core.database.PetSaveDatabase
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.daos.RemoteKeysDao
import dev.sihuynh.petsave.core.database.model.AnimalAggregate
import dev.sihuynh.petsave.core.database.model.OrganizationEntity
import dev.sihuynh.petsave.core.database.model.RemoteKeys
import dev.sihuynh.petsave.core.network.PetSaveNetworkDataSource
import dev.sihuynh.petsave.core.network.model.mappers.NetworkAnimalMapper
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class AnimalRemoteMediator (
    private val network: PetSaveNetworkDataSource,
    private val animalsDao: AnimalsDao,
    private val animalMapper: NetworkAnimalMapper,
    private val remoteKeysDao: RemoteKeysDao,
    private val organizationsDao: OrganizationsDao,
    private val database: PetSaveDatabase,
) : RemoteMediator<Int, AnimalAggregate>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, AnimalAggregate>): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = network.getNearByAnimals(
                pageToLoad = page,
                pageSize = state.config.pageSize,
                postcode = "07097",
                maxDistance = 100,
            )
            val animals = response.animals.orEmpty()

            val endOfPaginationReached = animals.isEmpty()
            database.performTransaction {
                if (loadType == LoadType.REFRESH) {
                    animalsDao.deleteAll()
                    organizationsDao.deleteAll()
                    remoteKeysDao.deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = animals.map {
                    RemoteKeys(animalId = it.id, prevKey, nextKey)
                }
                remoteKeysDao.insertAll(keys)

                val animalsDomain = animals
                    .map { animalMapper.mapToDomain(it) }
                organizationsDao.insert(
                    animalsDomain
                        .map { OrganizationEntity.fromDomain(it.details.organization) }
                )
                animalsDao.insertAnimalsWithDetails(
                    animalsDomain.map { AnimalAggregate.fromDomain(it) }
                )
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimalAggregate>): RemoteKeys? =
        state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeyByAnimalId(item.animal.animalId)
            }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimalAggregate>): RemoteKeys? =
        state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeyByAnimalId(item.animal.animalId)
            }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimalAggregate>): RemoteKeys? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.animal?.animalId?.let {
                remoteKeysDao.remoteKeyByAnimalId(it)
            }
        }
}

private const val STARTING_PAGE_INDEX = 1