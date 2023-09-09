package dev.sihuynh.petsave.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesAnimalsDao(database: PetSaveDatabase): AnimalsDao
        = database.animalsDao()

    @Provides
    fun providesOrganizationDao(database: PetSaveDatabase): OrganizationsDao
        = database.organizationsDao()
}