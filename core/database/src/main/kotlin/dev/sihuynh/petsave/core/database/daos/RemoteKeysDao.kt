package dev.sihuynh.petsave.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sihuynh.petsave.core.database.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE animalId = :animalId")
    suspend fun remoteKeyByAnimalId(animalId: Long): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}