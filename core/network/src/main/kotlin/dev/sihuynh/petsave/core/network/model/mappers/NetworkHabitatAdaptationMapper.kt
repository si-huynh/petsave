package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.details.HabitatAdaptation
import dev.sihuynh.petsave.core.network.model.NetworkEnvironment
import javax.inject.Inject

class NetworkHabitatAdaptationMapper @Inject constructor():
    NetworkMapper<NetworkEnvironment?, HabitatAdaptation> {
    override fun mapToDomain(networkEntity: NetworkEnvironment?): HabitatAdaptation {
        return HabitatAdaptation(
            goodWithChildren = networkEntity?.children ?: true,
            goodWithDogs = networkEntity?.dogs ?: true,
            goodWithCats = networkEntity?.cats ?: true
        )
    }
}