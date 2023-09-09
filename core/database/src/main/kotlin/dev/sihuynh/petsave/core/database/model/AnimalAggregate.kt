package dev.sihuynh.petsave.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails

data class AnimalAggregate(
    @Embedded
    val animal: AnimalWithDetailsEntity,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "animalId"
    )
    val photos: List<PhotoEntity>,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "animalId"
    )
    val videos: List<VideoEntity>,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "tag",
        associateBy = Junction(AnimalTagCrossRefEntity::class)
    )
    val tags: List<TagEntity>
) {
    companion object {
        fun fromDomain(domainModel: AnimalWithDetails): AnimalAggregate {
            return AnimalAggregate(
                animal = AnimalWithDetailsEntity.fromDomain(domainModel),
                photos = domainModel.media.photos.map { photo ->
                    PhotoEntity.fromDomain(domainModel.id, photo)
                },
                videos = domainModel.media.videos.map { video ->
                    VideoEntity.fromDomain(domainModel.id, video)
                },
                tags = domainModel.tags.map { TagEntity(it) }
            )
        }
    }
}