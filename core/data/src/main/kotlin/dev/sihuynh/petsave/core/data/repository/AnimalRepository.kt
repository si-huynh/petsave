package dev.sihuynh.petsave.core.data.repository

import androidx.paging.PagingData
import dev.sihuynh.petsave.core.database.model.AnimalAggregate
import dev.sihuynh.petsave.core.model.animal.Animal
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails
import dev.sihuynh.petsave.core.model.pagination.PaginatedAnimals
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getAnimalStream(): Flow<PagingData<AnimalAggregate>>

    fun getAnimals(): Flow<List<Animal>>

    suspend fun requestMoreAnimals(
        pageToLoad: Int,
        numberOfItems: Int
    ): PaginatedAnimals

    suspend fun storeAnimals(animals: List<AnimalWithDetails>)
}