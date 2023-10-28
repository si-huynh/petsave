package dev.sihuynh.petsave.core.domain

import dev.sihuynh.petsave.core.data.repository.AnimalRepository
import dev.sihuynh.petsave.core.model.NoMoreAnimalsException
import dev.sihuynh.petsave.core.model.pagination.Pagination
import javax.inject.Inject

class RequestMoreAnimalsUseCase @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (animals, pagination) = animalRepository.requestMoreAnimals(pageToLoad, pageSize)
        if (animals.isEmpty()) {
            throw NoMoreAnimalsException("No animals nearby :(")
        }
        animalRepository.storeAnimals(animals)
        return pagination
    }
}