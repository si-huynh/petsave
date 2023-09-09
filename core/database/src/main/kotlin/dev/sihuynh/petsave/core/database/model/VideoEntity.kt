package dev.sihuynh.petsave.core.database.model

import androidx.room.ColumnInfo
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
            parentColumns = ["animal_id"],
            childColumns = ["animal_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("animal_id")]
)
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "video_id")
    val videoId: Long = 0,
    @ColumnInfo(name = "animal_id")
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
