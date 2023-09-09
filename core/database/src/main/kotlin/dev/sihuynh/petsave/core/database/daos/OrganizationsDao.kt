package dev.sihuynh.petsave.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.sihuynh.petsave.core.database.model.OrganizationEntity

@Dao
interface OrganizationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(organizationList: List<OrganizationEntity>)
}