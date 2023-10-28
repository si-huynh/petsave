package dev.sihuynh.petsave.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.daos.RemoteKeysDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesAnimalsDao(database: PetSaveDatabase): AnimalsDao
        = database.animalsDao()

    @Provides
    fun providesOrganizationDao(database: PetSaveDatabase): OrganizationsDao
        = database.organizationsDao()

    @Provides
    fun providesRemoteKeysDao(database: PetSaveDatabase): RemoteKeysDao
        = database.remoteKeysDao()
}