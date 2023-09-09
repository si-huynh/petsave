package dev.sihuynh.petsave.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.sihuynh.petsave.core.database.PetSaveDatabase
import dev.sihuynh.petsave.core.database.daos.AnimalsDao
import dev.sihuynh.petsave.core.database.daos.OrganizationsDao
import dev.sihuynh.petsave.core.database.model.AnimalWithDetailsEntity
import dev.sihuynh.petsave.core.database.model.OrganizationEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AnimalsDaoTest {

    private lateinit var animalsDao: AnimalsDao
    private lateinit var organizationsDao: OrganizationsDao
    private lateinit var db: PetSaveDatabase

    @Before
    fun setupDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = PetSaveDatabase::class.java
        ).build()
        animalsDao = db.animalsDao()
        organizationsDao = db.organizationsDao()
    }

    @Test
    fun animalsDao_fetches_items_by_descending_publish_date() = runTest {
        // Given
        val entities = listOf(
            testAnimal(id = 0, millisSinceEpoch = 0L),
            testAnimal(id = 1, millisSinceEpoch = 3L),
            testAnimal(id = 2, millisSinceEpoch = 1L),
            testAnimal(id = 3, millisSinceEpoch = 2L)
        )

        organizationsDao.insert(listOf(testOrganization()))

        // When
        animalsDao.upsertAnimalsWithDetails(entities)

        // Then
        val savedAnimalEntities = animalsDao.getAllAnimals().first()

        assertEquals(
            listOf(3L, 2L, 1L, 0L),
            savedAnimalEntities.map {
                it.animal.publishedAt.toEpochMilliseconds()
            }
        )
    }
}

private fun testAnimal(id: Long, millisSinceEpoch: Long)
    = AnimalWithDetailsEntity(
        animalId = id,
        organizationId = "0",
        name = "",
        type = "",
        description = "",
        age = "",
        species = "",
        primaryBreed = "",
        secondaryBreed = "",
        primaryColor = "",
        secondaryColor = "",
        tertiaryColor = "",
        gender = "",
        size = "",
        coat = "",
        isSpayedOrNeutered = false,
        isDeclawed = false,
        hasSpecialNeeds = false,
        shotsAreCurrent = false,
        goodWithChildren = false,
        goodWithDogs = false,
        goodWithCats = false,
        adoptionStatus = "",
        publishedAt = Instant.fromEpochMilliseconds(millisSinceEpoch)
    )

private fun testOrganization()
    = OrganizationEntity(
        organizationId = "0",
        email = "",
        phone = "",
        address1 = "",
        address2 = "",
        city = "",
        state = "",
        postcode = "",
        country = "",
        distance = 0f
    )