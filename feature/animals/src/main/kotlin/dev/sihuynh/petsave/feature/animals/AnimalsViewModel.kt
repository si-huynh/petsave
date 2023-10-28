package dev.sihuynh.petsave.feature.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sihuynh.petsave.core.data.repository.AnimalRepository
import dev.sihuynh.petsave.core.model.animal.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
): ViewModel() {

    /**
     * Stream of immutable states representative of the UI
     */
    val pagingDataFlow: Flow<PagingData<AnimalsUiState>>

    init {
        pagingDataFlow = MutableSharedFlow<UiAction>()
            .filterIsInstance<UiAction.LoadAnimals>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.LoadAnimals) }
            .flatMapLatest { getPaginatedAnimals() }
            .cachedIn(viewModelScope)
    }

    private fun getPaginatedAnimals(): Flow<PagingData<AnimalsUiState>> =
        animalRepository.getAnimalStream().map { pagingData ->
            pagingData
                .map { it.animal.toAnimalDomain(it.photos, it.videos, it.tags) }
                .map { AnimalsUiState.Paging(it) }
        }
}

sealed interface UiAction {
    data object LoadAnimals : UiAction
}

sealed class AnimalsUiState {
    data class Paging(val animal: Animal) : AnimalsUiState()
}