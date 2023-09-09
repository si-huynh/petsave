package dev.sihuynh.petsave.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.model.AnimalTagCrossRefEntity
import dev.sihuynh.petsave.core.database.model.AnimalWithDetailsEntity
import dev.sihuynh.petsave.core.database.model.OrganizationEntity
import dev.sihuynh.petsave.core.database.model.PhotoEntity
import dev.sihuynh.petsave.core.database.model.TagEntity
import dev.sihuynh.petsave.core.database.model.VideoEntity
import dev.sihuynh.petsave.core.database.util.InstantConverter

@Database(
    entities = [
        AnimalTagCrossRefEntity::class,
        AnimalWithDetailsEntity::class,
        OrganizationEntity::class,
        PhotoEntity::class,
        TagEntity::class,
        VideoEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverter::class)
abstract class PetSaveDatabase : RoomDatabase() {
    abstract fun organizationsDao(): OrganizationsDao

    abstract fun animalsDao(): AnimalsDao
}