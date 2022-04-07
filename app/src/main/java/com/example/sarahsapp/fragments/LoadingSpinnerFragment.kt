package com.example.sarahsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sarahsapp.R

class LoadingSpinnerFragment : Fragment() {

    private lateinit var splashScreenImage: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loading_spinner, container, false)
        splashScreenImage = view.findViewById(R.id.moneySarah)
        animateMoneySarah()
        splashScreenImage.setOnClickListener {
            val action = LoadingSpinnerFragmentDirections.actionLoadingSpinnerFragmentToExchangesListFragment()
            view.findNavController().navigate(action)
        }

        return view
    }

    private fun animateMoneySarah(){
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        splashScreenImage.animation = rotate
    }
}