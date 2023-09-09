package dev.sihuynh.petsave.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import dev.sihuynh.petsave.core.database.model.AnimalAggregate
import dev.sihuynh.petsave.core.database.model.AnimalWithDetailsEntity
import dev.sihuynh.petsave.core.database.model.PhotoEntity
import dev.sihuynh.petsave.core.database.model.TagEntity
import dev.sihuynh.petsave.core.database.model.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnimalsDao {
    @Transaction
    @Query("SELECT * FROM animals")
    abstract fun getAllAnimals(): Flow<List<AnimalAggregate>>

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertAnimalAggregate(
        animal: AnimalWithDetailsEntity,
        photos: List<PhotoEntity>,
        videos: List<VideoEntity>,
        tags: List<TagEntity>
    )

    suspend fun insertAnimalsWithDetails(animalAggregates: List<AnimalAggregate>) {
        for (aggregate in animalAggregates) {
            insertAnimalAggregate(
                animal = aggregate.animal,
                photos = aggregate.photos,
                videos = aggregate.videos,
                tags = aggregate.tags,
            )
        }
    }
}