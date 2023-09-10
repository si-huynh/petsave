package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.pagination.Pagination
import dev.sihuynh.petsave.core.network.model.NetworkPagination
import javax.inject.Inject

class NetworkPaginationMapper @Inject constructor():
    NetworkMapper<NetworkPagination?, Pagination> {
    override fun mapToDomain(networkEntity: NetworkPagination?): Pagination {
        return Pagination(
            currentPage = networkEntity?.currentPage ?: 0,
            totalPage = networkEntity?.totalPages ?: 0
        )
    }
}