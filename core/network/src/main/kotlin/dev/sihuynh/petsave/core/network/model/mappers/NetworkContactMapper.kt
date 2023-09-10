package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.organization.Organization
import dev.sihuynh.petsave.core.network.model.NetworkContact
import javax.inject.Inject

class NetworkContactMapper @Inject constructor(
    private val networkAddressMapper: NetworkAddressMapper
): NetworkMapper<NetworkContact?, Organization.Contact> {
    override fun mapToDomain(networkEntity: NetworkContact?): Organization.Contact {
        return Organization.Contact(
            email = networkEntity?.email.orEmpty(),
            phone = networkEntity?.phone.orEmpty(),
            address = networkAddressMapper.mapToDomain(networkEntity?.address)
        )
    }
}