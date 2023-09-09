package dev.sihuynh.petsave.core.model.animal

import kotlinx.datetime.Instant

data class Animal(
    val id: Long,
    val name: String,
    val type: String,
    val media: Media,
    val tags: List<String>,
    val adoptionStatus: AdoptionStatus,
    val publishedAt: Instant
)