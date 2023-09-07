package dev.sihuynh.petsave.core.model.pagination

import dev.sihuynh.petsave.core.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)
