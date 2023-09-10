package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.Media
import dev.sihuynh.petsave.core.network.model.NetworkVideoLink
import javax.inject.Inject

class NetworkVideoMapper @Inject constructor(): NetworkMapper<NetworkVideoLink?, Media.Video> {
    override fun mapToDomain(networkEntity: NetworkVideoLink?): Media.Video {
        return Media.Video(video = networkEntity?.embed.orEmpty())
    }
}