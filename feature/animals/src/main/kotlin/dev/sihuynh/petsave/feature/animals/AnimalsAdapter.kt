package dev.sihuynh.petsave.feature.animals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import dev.sihuynh.petsave.core.model.animal.Animal
import dev.sihuynh.petsave.feature.animals.databinding.RecyclerViewAnimalItemBinding

class AnimalsAdapter : PagingDataAdapter<AnimalsUiState, AnimalsAdapter.AnimalsViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        val binding = RecyclerViewAnimalItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalsViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        val uiModel = getItem(position)
        if (uiModel is AnimalsUiState.Paging) {
            holder.bind(uiModel.animal)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<AnimalsUiState>() {
            override fun areContentsTheSame(oldItem: AnimalsUiState, newItem: AnimalsUiState): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: AnimalsUiState, newItem: AnimalsUiState): Boolean {
                return (oldItem is AnimalsUiState.Paging && newItem is AnimalsUiState.Paging &&
                        oldItem.animal.id == newItem.animal.id)
            }
        }
    }

    inner class AnimalsViewHolder(
        private val binding: RecyclerViewAnimalItemBinding
    ): ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            binding.name.text = animal.name
            binding.photo.load(animal.media.getFirstSmallestAvailablePhoto())
        }
    }
}