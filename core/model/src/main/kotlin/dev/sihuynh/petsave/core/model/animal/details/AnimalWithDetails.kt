package dev.sihuynh.petsave.core.model.animal.details

import dev.sihuynh.petsave.core.model.animal.AdoptionStatus
import dev.sihuynh.petsave.core.model.animal.Media
import java.time.LocalDateTime

data class AnimalWithDetails(
    val id: Long,
    val name: String,
    val type: String,
    val details: Details,
    val media: Media,
    val tags: List<String>,
    val adoptionStatus: AdoptionStatus,
    val publishedAt: LocalDateTime
)
