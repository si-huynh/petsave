package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.details.HealthDetails
import dev.sihuynh.petsave.core.network.model.NetworkAttributes
import javax.inject.Inject

class NetworkHealthDetailsMapper @Inject constructor():
    NetworkMapper<NetworkAttributes?, HealthDetails> {
    override fun mapToDomain(networkEntity: NetworkAttributes?): HealthDetails {
        return HealthDetails(
            isSpayedOrNeutered = networkEntity?.spayedNeutered ?: false,
            isDeclawed = networkEntity?.declawed ?: false,
            hasSpecialNeeds = networkEntity?.specialNeeds ?: false,
            shotsAreCurrent = networkEntity?.shotsCurrent ?: false
        )
    }
}