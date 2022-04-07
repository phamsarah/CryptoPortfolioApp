package com.example.sarahsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.data.model.CurrencyData
import com.example.sarahsapp.databinding.CurrencyListItemBinding

class CurrencyRecyclerViewAdapter(private val currencyValues: List<CurrencyData>): RecyclerView.Adapter<CurrencyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyData = currencyValues[position]
        holder.bind(currencyData)
    }

    override fun getItemCount(): Int = currencyValues.size

    inner class ViewHolder(binding: CurrencyListItemBinding): RecyclerView.ViewHolder(binding.root){
        private val currencyName: TextView = binding.currencyName
        private val currencyBalance: TextView = binding.currencyBalance

        fun bind(currencyData: CurrencyData){
            currencyName.text = currencyData.name
            currencyBalance.text = ("%.6f".format(currencyData.balance.toFloat()))
        }
    }
}