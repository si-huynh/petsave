package dev.sihuynh.petsave.feature.animals

import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sihuynh.petsave.core.data.repository.AnimalRepository
import dev.sihuynh.petsave.core.model.animal.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

class AnimalsViewModel(
    private val animalRepository: AnimalRepository
) {

    /**
     * Stream of immutable states representative of the UI
     */
    // val pagingDataFlow: Flow<PagingData<AnimalsUiState>>

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()

    }
}

sealed interface UiAction {
    object Scroll: UiAction
}

sealed interface AnimalsUiState {
    data class Success(val animals: List<Animal>): AnimalsUiState
    object Error: AnimalsUiState
    object Loading: AnimalsUiState
}