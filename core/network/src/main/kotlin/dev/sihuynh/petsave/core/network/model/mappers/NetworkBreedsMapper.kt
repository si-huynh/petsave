package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.details.Breed
import dev.sihuynh.petsave.core.network.model.NetworkBreeds
import javax.inject.Inject

class NetworkBreedsMapper @Inject constructor(): NetworkMapper<NetworkBreeds?, Breed> {
    override fun mapToDomain(networkEntity: NetworkBreeds?): Breed {
        return Breed(
            primary = networkEntity?.primary.orEmpty(),
            secondary = networkEntity?.secondary.orEmpty()
        )
    }
}