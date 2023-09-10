package dev.sihuynh.petsave.core.data.repository

import dev.sihuynh.petsave.core.model.animal.Animal
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails
import dev.sihuynh.petsave.core.model.pagination.PaginatedAnimals
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getAnimals(): Flow<List<Animal>>

    suspend fun requestMoreAnimals(
        pageToLoad: Int,
        numberOfItems: Int
    ): PaginatedAnimals

    suspend fun storeAnimals(animals: List<AnimalWithDetails>)
}