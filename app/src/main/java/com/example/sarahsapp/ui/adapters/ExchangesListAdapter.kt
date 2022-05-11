package com.example.sarahsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.data.model.Exchange
import com.example.sarahsapp.databinding.ExchangesItemLayoutBinding
import com.example.sarahsapp.ui.viewholders.ExchangeViewHolder


class ExchangesListAdapter(private val listener: ExchangesItemListener): RecyclerView.Adapter<ExchangeViewHolder>() {

    interface ExchangesItemListener{
        fun onExchangeClicked(cardView: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        return ExchangeViewHolder(
            ExchangesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent,
            listener
        )
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        val exchange = Exchange.exchangeList[position]
        holder.bind(exchange, position)
    }

    override fun getItemCount(): Int = Exchange.exchangeList.size


}