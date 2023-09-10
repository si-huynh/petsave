package dev.sihuynh.petsave.core.network.model.mappers

import dev.sihuynh.petsave.core.model.animal.AdoptionStatus
import dev.sihuynh.petsave.core.model.animal.Media
import dev.sihuynh.petsave.core.model.animal.details.Age
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails
import dev.sihuynh.petsave.core.model.animal.details.Coat
import dev.sihuynh.petsave.core.model.animal.details.Details
import dev.sihuynh.petsave.core.model.animal.details.Gender
import dev.sihuynh.petsave.core.model.animal.details.Size
import dev.sihuynh.petsave.core.model.organization.Organization
import dev.sihuynh.petsave.core.network.model.NetworkAnimal
import dev.sihuynh.petsave.core.network.model.parseWithBasicOffset
import kotlinx.datetime.Instant
import javax.inject.Inject

class NetworkAnimalMapper @Inject constructor(
    private val breedsMapper: NetworkBreedsMapper,
    private val colorsMapper: NetworkColorsMapper,
    private val healthDetailsMapper: NetworkHealthDetailsMapper,
    private val habitatAdaptationMapper: NetworkHabitatAdaptationMapper,
    private val photoMapper: NetworkPhotoMapper,
    private val videoMapper: NetworkVideoMapper,
    private val contactMapper: NetworkContactMapper
): NetworkMapper<NetworkAnimal, AnimalWithDetails> {
    override fun mapToDomain(networkEntity: NetworkAnimal): AnimalWithDetails {
        return AnimalWithDetails(
            id = networkEntity.id ?: throw MappingException("Animal ID cannot be nulll"),
            name = networkEntity.name.orEmpty(),
            type = networkEntity.type.orEmpty(),
            details = parseAnimalDetails(networkEntity),
            media = mapMedia(networkEntity),
            tags = networkEntity.tags.orEmpty().map { it.orEmpty() },
            adoptionStatus = parseAdoptionStatus(networkEntity.status),
            publishedAt = Instant.parseWithBasicOffset(networkEntity.publishedAt)
        )
    }

    private fun parseAnimalDetails(apiAnimal: NetworkAnimal): Details {
        return Details(
            description = apiAnimal.description.orEmpty(),
            age = parseAge(apiAnimal.age),
            species = apiAnimal.species.orEmpty(),
            breed = breedsMapper.mapToDomain(apiAnimal.breeds),
            colors = colorsMapper.mapToDomain(apiAnimal.colors),
            gender = parserGender(apiAnimal.gender),
            size = parseSize(apiAnimal.size),
            coat = parseCoat(apiAnimal.coat),
            healthDetails = healthDetailsMapper.mapToDomain(apiAnimal.attributes),
            habitatAdaptation = habitatAdaptationMapper.mapToDomain(apiAnimal.environment),
            organization = mapOrganization(apiAnimal)
        )
    }

    private fun parseAge(age: String?): Age {
        if (age.isNullOrEmpty()) return Age.UNKNOWN

        // will throw IllegalStateException if the string does not match any enum value
        return Age.valueOf(age.uppercase())
    }

    private fun parserGender(gender: String?): Gender {
        if (gender.isNullOrEmpty()) return Gender.UNKNOWN

        return Gender.valueOf(gender.uppercase())
    }

    private fun parseSize(size: String?): Size {
        if (size.isNullOrEmpty()) return Size.UNKNOWN

        return Size.valueOf(
            size.replace(' ', '_').uppercase()
        )
    }

    private fun parseCoat(coat: String?): Coat {
        if (coat.isNullOrEmpty()) return Coat.UNKNOWN

        return Coat.valueOf(coat.uppercase())
    }

    private fun mapMedia(apiAnimal: NetworkAnimal): Media {
        return Media(
            photos = apiAnimal.photos?.map { photoMapper.mapToDomain(it) }.orEmpty(),
            videos = apiAnimal.videos?.map { videoMapper.mapToDomain(it) }.orEmpty()
        )
    }

    private fun parseAdoptionStatus(status: String?): AdoptionStatus {
        if (status.isNullOrEmpty()) return AdoptionStatus.UNKNOWN

        return AdoptionStatus.valueOf(status.uppercase())
    }

    private fun mapOrganization(apiAnimal: NetworkAnimal): Organization {
        return Organization(
            id = apiAnimal.organizationId ?: throw MappingException("Organization ID cannot be null"),
            contact = contactMapper.mapToDomain(apiAnimal.contact),
            distance = apiAnimal.distance ?: -1f
        )
    }
}