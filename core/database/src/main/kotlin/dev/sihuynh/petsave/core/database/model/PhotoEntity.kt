package dev.sihuynh.petsave.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.sihuynh.petsave.core.model.animal.Media

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = AnimalWithDetailsEntity::class,
            parentColumns = ["animalId"],
            childColumns = ["animalId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("animalId")]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val photoId: Long = 0,
    val animalId: Long,
    val medium: String,
    val full: String
) {
    companion object {
        fun fromDomain(animalId: Long, photo: Media.Photo): PhotoEntity {
            val (medium, full) = photo
            return PhotoEntity(
                animalId = animalId,
                medium = medium,
                full = full
            )
        }
    }

    fun toDomain(): Media.Photo = Media.Photo(medium, full)
}