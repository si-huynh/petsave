package dev.sihuynh.petsave.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sihuynh.petsave.core.model.organization.Organization

@Entity(tableName = "organizations")
data class OrganizationEntity(
    @PrimaryKey(autoGenerate = false)
    val organizationId: String,
    val email: String,
    val phone: String,
    val address1: String,
    val address2: String,
    val city: String,
    val state: String,
    val postcode: String,
    val country: String,
    val distance: Float
) {
    companion object {
        fun fromDomain(domainModel: Organization): OrganizationEntity {
            val contact = domainModel.contact
            val address = contact.address

            return OrganizationEntity(
                organizationId = domainModel.id,
                email = contact.email,
                phone = contact.phone,
                address1 = address.address1,
                address2 = address.address2,
                city = address.city,
                state = address.state,
                postcode = address.postcode,
                country = address.country,
                distance = domainModel.distance
            )
        }
    }

    fun toDomain(): Organization {
        return Organization(
            id = organizationId,
            contact = Organization.Contact(
                email = email,
                phone = phone,
                address = Organization.Address(
                    address1 = address1,
                    address2 = address2,
                    city = city,
                    state = state,
                    postcode = postcode,
                    country = country,
                )
            ),
            distance = distance
        )
    }
}