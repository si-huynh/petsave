package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.Media
import dev.sihuynh.petsave.core.network.model.NetworkPhotoSizes
import javax.inject.Inject

class NetworkPhotoMapper @Inject constructor(): NetworkMapper<NetworkPhotoSizes?, Media.Photo> {
    override fun mapToDomain(networkEntity: NetworkPhotoSizes?): Media.Photo {
        return Media.Photo(
            medium = networkEntity?.medium.orEmpty(),
            full = networkEntity?.full.orEmpty()
        )
    }
}