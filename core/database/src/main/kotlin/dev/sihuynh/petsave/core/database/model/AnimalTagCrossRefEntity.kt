package dev.sihuynh.petsave.core.database.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["animalId", "tag"],
    indices = [Index("tag")]
)
data class AnimalTagCrossRefEntity(
    val animalId: Long,
    val tag: String
)
