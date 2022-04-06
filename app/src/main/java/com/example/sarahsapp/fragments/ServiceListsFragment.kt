package com.example.sarahsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sarahsapp.databinding.ServiceListsFragmentBinding
import com.example.sarahsapp.ui.adapters.ServiceItemAdapter

class ServiceListsFragment : Fragment() {
    private lateinit var _binding: ServiceListsFragmentBinding

    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ServiceListsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = ServiceItemAdapter()
    }
}