package com.example.sarahsapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sarahsapp.ui.viewmodels.PortfolioListsViewModel
import com.example.sarahsapp.R

class PortfolioListsFragment : Fragment() {

    companion object {
        fun newInstance() = PortfolioListsFragment()
    }

    private lateinit var viewModel: PortfolioListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.portfolio_lists_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PortfolioListsViewModel::class.java]
        // TODO: Use the ViewModel
    }

}