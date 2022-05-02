package com.example.sarahsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.ExchangesListsFragmentBinding
import com.example.sarahsapp.ui.adapters.ExchangesListAdapter
import com.google.android.material.transition.MaterialElevationScale

/**
 * Home Fragment
 */
class ExchangesListsFragment : Fragment(), ExchangesListAdapter.ExchangesItemListener {
    private lateinit var _binding: ExchangesListsFragmentBinding

    private val binding get() = _binding

    private val exchangesListAdapter = ExchangesListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExchangesListsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        binding.recyclerView.adapter = exchangesListAdapter
    }

    // Navigate to different fragments based on the position of the Exchange card clicked
    override fun onExchangeClicked(cardView: View, position: Int) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.open_exchange_details_transition_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.open_exchange_details_transition_duration).toLong()
        }

        val exchangeItemCardTransition = getString(R.string.exchange_item_card_transition)
        val extras = FragmentNavigatorExtras(cardView to exchangeItemCardTransition)
        lateinit var directions: NavDirections

        when (position) {
            0 -> directions = ExchangesListsFragmentDirections.actionExchangesListFragmentToCoinbaseProFragment()
            1 -> directions = ExchangesListsFragmentDirections.actionExchangesListsFragmentToCoinbaseFragment()
            2 -> directions = ExchangesListsFragmentDirections.actionExchangesListFragmentToNicehashFragment()
        }

        findNavController().navigate(directions, extras)
    }
}