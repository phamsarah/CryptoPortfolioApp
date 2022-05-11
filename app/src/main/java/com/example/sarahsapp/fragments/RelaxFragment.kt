package com.example.sarahsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.RelaxFragmentBinding
import kotlin.random.Random

class RelaxFragment: Fragment() {

    private var _binding: RelaxFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RelaxFragmentBinding.inflate(inflater,container,false)

        val randomInt = Random.nextInt(imageList.size)

        binding.imageView.setImageResource(imageList[randomInt])
        return binding.root
    }

    companion object{
        val imageList: List<Int> = listOf(R.drawable.lofi_lakehouse, R.drawable.lofi)
    }
}