package dev.sihuynh.petsave.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPaginatedAnimals(
    @SerialName("animals") val animals: List<NetworkAnimal>?,
    @SerialName("pagination") val pagination: NetworkPagination?,
)

@Serializable
data class NetworkPagination(
    @SerialName("count_per_page") val countPerPage: Int?,
    @SerialName("total_count") val totalCount: Int?,
    @SerialName("current_page") val currentPage: Int?,
    @SerialName("total_pages") val totalPages: Int?,
)
