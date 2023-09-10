package dev.sihuynh.petsave.core.data

import dev.sihuynh.petsave.core.datastore.ChangeListVersions

/**
 * Interface maker for a class that manages synchronization between local data and a remote
 * source for a [Syncable]
 */
interface Synchronizer {
    suspend fun getChangeListVersions(): ChangeListVersions

    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)

    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

/**
 * Interface maker for a class that is synchronized with a remote source. Syncing must not be
 * perform concurrently and it is the [Synchronizer]'s responsibility to ensure this.
 */
interface Syncable {
    /**
     * Synchronizes the local database backing the repository with the network.
     * Returns if the sync was successful or not.
     */
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

/**
 * Utility function for syncing a repository with the network.
 */