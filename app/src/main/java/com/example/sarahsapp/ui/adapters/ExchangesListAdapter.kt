package com.example.sarahsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.data.model.Exchange
import com.example.sarahsapp.databinding.ExchangesItemLayoutBinding
import com.example.sarahsapp.ui.utils.themeStyle
import com.google.android.material.R.attr.*


class ExchangesListAdapter(private val listener: ExchangesItemListener): RecyclerView.Adapter<ExchangesListAdapter.ViewHolder>() {

    interface ExchangesItemListener{
        fun onExchangeClicked(cardView: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExchangesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent,
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchange = Exchange(position)
        holder.bind(exchange, position)
    }

    inner class ViewHolder(
        private val binding: ExchangesItemLayoutBinding,
        private val view: ViewGroup,
        listener: ExchangesItemListener
    ) : RecyclerView.ViewHolder(binding.root){

        private val nameView: TextView = binding.exchangeName
        private val imageView: ImageView = binding.exchangeImage

        init{
            binding.run {
                this.listener = listener
            }
        }

        @SuppressLint("NewApi")
        fun bind(exchange: Exchange, position: Int){
            nameView.text = view.context.getString(exchange.name)
            imageView.setImageResource(exchange.image)

            binding.position = position

            val textAppearance = nameView.context.themeStyle(
                textAppearanceHeadline5
            )
            nameView.setTextAppearance(
                textAppearance
            )

            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = 3

}