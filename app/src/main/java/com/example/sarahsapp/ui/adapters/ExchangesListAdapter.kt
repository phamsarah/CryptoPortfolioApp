package com.example.sarahsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.R
import com.example.sarahsapp.data.model.Exchange
import com.example.sarahsapp.databinding.ExchangesItemLayoutBinding
import com.example.sarahsapp.fragments.ExchangesListsFragmentDirections
import com.example.sarahsapp.ui.utils.themeStyle
import com.google.android.material.R.attr.*
import com.google.android.material.card.MaterialCardView


class ExchangesListAdapter: RecyclerView.Adapter<ExchangesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ExchangesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchange = Exchange(position)
        holder.bind(exchange, position)
    }

    inner class ViewHolder(binding: ExchangesItemLayoutBinding, private val view: ViewGroup) : RecyclerView.ViewHolder(binding.root){
        private val nameView: TextView = binding.name
        private val imageView: ImageView = binding.image
        private val exchangeItem: MaterialCardView = binding.cardView

        @SuppressLint("NewApi")
        fun bind(exchange: Exchange, position: Int){
            nameView.text = view.context.getString(exchange.name)
            imageView.setImageResource(exchange.image)

            val textAppearance = nameView.context.themeStyle(
                textAppearanceHeadline5
            )
            nameView.setTextAppearance(
                textAppearance
            )

            exchangeItem.setOnClickListener {
                when(position) {
                    0 -> {
                        val exchangeItemCardTransition = view.context.getString(R.string.exchange_item_card_transition)
                        val extras = FragmentNavigatorExtras(exchangeItem to exchangeItemCardTransition)
                        val directions = ExchangesListsFragmentDirections.actionExchangesListFragmentToCoinbaseProFragment()
                        view.findNavController().navigate(directions, extras)
                    }
                    // TODO: Apply transition here too
                    // TODO: backbutton
                    1 -> {
                        val action = ExchangesListsFragmentDirections.actionExchangesListsFragmentToCoinbaseFragment()
                        view.findNavController().navigate(action)
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int = 4

}