package com.example.sarahsapp.ui.viewholders

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.databinding.ExchangesItemLayoutBinding
import com.example.sarahsapp.ui.adapters.ExchangesListAdapter
import com.example.sarahsapp.ui.utils.themeStyle
import com.google.android.material.R

class ExchangeViewHolder(private val binding: ExchangesItemLayoutBinding, private val view: ViewGroup, listener: ExchangesListAdapter.ExchangesItemListener) : RecyclerView.ViewHolder(binding.root) {

    private val nameView: TextView = binding.exchangeName
    private val imageView: ImageView = binding.exchangeImage

    init {
        binding.run {
            this.listener = listener
        }
    }

    @SuppressLint("NewApi")
    fun bind(exchange: Pair<Int, Int>, position: Int) {
        nameView.text = view.context.getString(exchange.first)
        imageView.setImageResource(exchange.second)

        binding.position = position

        val textAppearance = nameView.context.themeStyle(
            R.attr.textAppearanceHeadline5
        )
        nameView.setTextAppearance(
            textAppearance
        )

        binding.executePendingBindings()
    }

}