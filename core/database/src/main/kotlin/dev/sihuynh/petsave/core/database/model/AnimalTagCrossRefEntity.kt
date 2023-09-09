package dev.sihuynh.petsave.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["animal_id", "tag"],
    indices = [Index("tag")]
)
data class AnimalTagCrossRefEntity(
    @ColumnInfo(name = "animal_id")
    val animalId: Long,
    val tag: String
)
