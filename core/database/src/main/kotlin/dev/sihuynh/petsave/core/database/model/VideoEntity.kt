package dev.sihuynh.petsave.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.sihuynh.petsave.core.model.animal.Media

@Entity(
    tableName = "videos",
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
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val videoId: Long = 0,
    val animalId: Long,
    val video: String
) {
    companion object {
        fun fromDomain(animalId: Long, domainModel: Media.Video): VideoEntity {
            return VideoEntity(
                animalId = animalId,
                video = domainModel.video
            )
        }
    }

    fun toDomain(): Media.Video = Media.Video(video)
}
