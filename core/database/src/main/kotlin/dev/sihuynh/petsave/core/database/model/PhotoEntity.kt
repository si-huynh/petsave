package dev.sihuynh.petsave.core.database.model

import androidx.room.ColumnInfo
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
            parentColumns = ["animal_id"],
            childColumns = ["animal_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("animal_id")]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photo_id")
    val photoId: Long = 0,
    @ColumnInfo(name = "animal_id")
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