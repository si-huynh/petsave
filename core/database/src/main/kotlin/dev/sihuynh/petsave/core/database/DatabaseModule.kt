package dev.sihuynh.petsave.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesPetSaveDatabase(
        @ApplicationContext context: Context
    ): PetSaveDatabase = Room.databaseBuilder(
        context = context,
        klass = PetSaveDatabase::class.java,
        name = "petsave-database"
    ).fallbackToDestructiveMigration().build()
}