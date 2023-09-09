package dev.sihuynh.petsave.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails

@Entity(
    tableName = "animals",
    foreignKeys = [
        ForeignKey(
            entity = OrganizationEntity::class,
            parentColumns = ["organizationId"],
            childColumns = ["organizationId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("organizationId")]
)
data class AnimalWithDetailsEntity(
    @PrimaryKey
    val animalId: Long,
    val organizationId: String,
    val name: String,
    val type: String,
    val description: String,
    val age: String,
    val species: String,
    val primaryBreed: String,
    val secondaryBreed: String,
    val primaryColor: String,
    val secondaryColor: String,
    val tertiaryColor: String,
    val gender: String,
    val size: String,
    val coat: String,
    val isSpayedOrNeutered: Boolean,
    val isDeclawed: Boolean,
    val hasSpecialNeeds: Boolean,
    val shotsAreCurrent: Boolean,
    val goodWithChildren: Boolean,
    val goodWithDogs: Boolean,
    val goodWithCats: Boolean,
    val adoptionStatus: String,
    val publishedAt: String
) {
    companion object {
        fun fromDomain(domainModel: AnimalWithDetails): AnimalWithDetailsEntity {
            val details = domainModel.details
            val healthDetails = details.healthDetails
            val habitatAdaptation = details.habitatAdaptation

            return AnimalWithDetailsEntity(
                animalId = domainModel.id,
                organizationId = details.organization.id,
                name = domainModel.name,
                type = domainModel.type,
                description = details.description,
                age = details.age.toString(),
                species = details.species,
                primaryBreed = details.breed.primary,
                secondaryBreed = details.breed.secondary,
                primaryColor = details.colors.primary,
                secondaryColor = details.colors.secondary,
                tertiaryColor = details.colors.tertiary,
                gender = details.gender.toString(),
                size = details.size.toString(),
                coat = details.coat.toString(),
                isSpayedOrNeutered = healthDetails.isSpayedOrNeutered,
                isDeclawed = healthDetails.isDeclawed,
                hasSpecialNeeds = healthDetails.hasSpecialNeeds,
                shotsAreCurrent = healthDetails.shotsAreCurrent,
                goodWithChildren = habitatAdaptation.goodWithChildren,
                goodWithDogs = habitatAdaptation.goodWithDogs,
                goodWithCats = habitatAdaptation.goodWithCats,
                adoptionStatus = domainModel.adoptionStatus.toString(),
                publishedAt = domainModel.publishedAt.toString()
            )
        }
    }
}