package dev.sihuynh.petsave.core.domain

import dev.sihuynh.petsave.core.data.repository.AnimalRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetAllAnimalsUseCase @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    operator fun invoke() = animalRepository.getAnimals()
        .filter { it.isNotEmpty() }
}