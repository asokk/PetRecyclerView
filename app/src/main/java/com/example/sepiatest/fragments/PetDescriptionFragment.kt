package com.example.sepiatest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sepiatest.databinding.FragmentPetDescriptionBinding

class PetDescriptionFragment : Fragment() {

    private var _binding: FragmentPetDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetDescriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.desc.text =  arguments?.getString("content_url")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}