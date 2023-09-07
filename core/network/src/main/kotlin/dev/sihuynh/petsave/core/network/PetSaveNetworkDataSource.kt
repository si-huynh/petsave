package dev.sihuynh.petsave.core.network

import dev.sihuynh.petsave.core.network.model.NetworkPaginatedAnimals

interface PetSaveNetworkDataSource {
    suspend fun getNearByAnimals(
        pageToLoad: Int,
        pageSize: Int,
        postcode: String,
        maxDistance: Int
    ): NetworkPaginatedAnimals

    suspend fun getAnimalsByType(type: String): NetworkPaginatedAnimals
}