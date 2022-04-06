package com.example.sarahsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.R
import com.example.sarahsapp.data.model.Service
import com.example.sarahsapp.databinding.ServiceItemLayoutBinding
import com.example.sarahsapp.fragments.ServiceListsFragmentDirections
import com.google.android.material.card.MaterialCardView

class ServiceItemAdapter: RecyclerView.Adapter<ServiceItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ServiceItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = Service(position)
        holder.bind(service, position)
    }

    inner class ViewHolder(binding: ServiceItemLayoutBinding, private val view: ViewGroup) : RecyclerView.ViewHolder(binding.root){
        private val nameView: TextView = binding.name
        private val imageView: ImageView = binding.image
        private val serviceItem: MaterialCardView = binding.cardView



        fun bind(service: Service, position: Int){
            nameView.text = view.context.getString(service.name)
            imageView.setImageResource(service.image)

            serviceItem.setOnClickListener {
                when(position) {
                    0 -> {
                        val action = ServiceListsFragmentDirections.actionServiceListsFragmentToCoinbaseProFragment()
                        view.findNavController().navigate(action)
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int = 4

}