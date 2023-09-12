package dev.sihuynh.petsave.feature.animals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.sihuynh.petsave.feature.animals.databinding.FragmentAnimalsBinding

class AnimalsFragment : Fragment() {

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
}