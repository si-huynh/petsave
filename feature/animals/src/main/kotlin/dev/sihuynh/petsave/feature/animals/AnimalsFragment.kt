package dev.sihuynh.petsave.feature.animals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sihuynh.petsave.feature.animals.databinding.FragmentAnimalsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalsFragment : Fragment() {
    private val viewModel: AnimalsViewModel by viewModels()
    private val binding get() = _binding!!
    private var _binding: FragmentAnimalsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindPagingState(
            pagingDataFlow = viewModel.pagingDataFlow,
        )
        // binding.bindState()
    }

    private fun FragmentAnimalsBinding.bindPagingState(
        pagingDataFlow: Flow<PagingData<AnimalsUiState>>,
    ) {
        val animalsAdapter = AnimalsAdapter()
        animalsRecyclerView.apply {
            adapter = animalsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            pagingDataFlow.collectLatest(animalsAdapter::submitData)
        }

        lifecycleScope.launch {
            animalsAdapter.loadStateFlow.collect { loadState ->
                animalsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading ||
                        loadState.mediator?.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.animalsRecyclerView.adapter = null
        _binding = null
    }
}