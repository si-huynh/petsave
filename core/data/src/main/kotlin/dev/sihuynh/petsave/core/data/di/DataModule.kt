package dev.sihuynh.petsave.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sihuynh.petsave.core.data.repository.AnimalRepository
import dev.sihuynh.petsave.core.data.repository.PetFinderAnimalRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsAnimalRepository(repository: PetFinderAnimalRepository): AnimalRepository
}