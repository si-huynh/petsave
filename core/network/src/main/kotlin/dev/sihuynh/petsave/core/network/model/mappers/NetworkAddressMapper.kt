package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.organization.Organization
import dev.sihuynh.petsave.core.network.model.NetworkAddress
import javax.inject.Inject

class NetworkAddressMapper @Inject constructor() : NetworkMapper<NetworkAddress?, Organization.Address> {
    override fun mapToDomain(networkEntity: NetworkAddress?): Organization.Address {
        return Organization.Address(
            address1 = networkEntity?.address1.orEmpty(),
            address2 = networkEntity?.address2.orEmpty(),
            city = networkEntity?.city.orEmpty(),
            state = networkEntity?.state.orEmpty(),
            postcode = networkEntity?.postcode.orEmpty(),
            country = networkEntity?.country.orEmpty()
        )
    }
}