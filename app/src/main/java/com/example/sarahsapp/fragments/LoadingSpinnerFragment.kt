package com.example.sarahsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.sarahsapp.R
import com.example.sarahsapp.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoadingSpinnerFragment : Fragment() {

    private lateinit var splashScreenImage: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loading_spinner, container, false)

        splashScreenImage = view.findViewById(R.id.moneySarah)
        animateMoneySarah()
        splashScreenImage.setOnClickListener {
            val actionRelax = LoadingSpinnerFragmentDirections.actionLoadingSpinnerFragmentToRelaxFragment()
            showBottomNavMenu()
            view.findNavController().navigate(actionRelax)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavMenu()
    }

    private fun hideBottomNavMenu(){
        (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        (requireActivity() as MainActivity).findViewById<View>(R.id.shadow_view).visibility = View.GONE
    }

    private fun showBottomNavMenu(){
        (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        (requireActivity() as MainActivity).findViewById<View>(R.id.shadow_view).visibility = View.VISIBLE
    }

    private fun animateMoneySarah(){
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        splashScreenImage.animation = rotate
    }
}