package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.details.Colors
import dev.sihuynh.petsave.core.network.model.NetworkColors
import javax.inject.Inject

class NetworkColorsMapper @Inject constructor(): NetworkMapper<NetworkColors?, Colors> {
    override fun mapToDomain(networkEntity: NetworkColors?): Colors {
        return Colors(
            primary = networkEntity?.primary.orEmpty(),
            secondary = networkEntity?.secondary.orEmpty(),
            tertiary = networkEntity?.tertiary.orEmpty()
        )
    }
}