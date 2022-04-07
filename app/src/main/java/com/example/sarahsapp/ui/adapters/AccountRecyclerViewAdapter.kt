package com.example.sarahsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.R
import com.example.sarahsapp.activities.MainActivity
import com.example.sarahsapp.data.model.CoinbaseProData
import com.example.sarahsapp.databinding.ExchangeDetailsCardBinding

class AccountRecyclerViewAdapter ( private val mainActivity: MainActivity, private val values: List<CoinbaseProData>) : RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ExchangeDetailsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = values[position]
        holder.bind(account)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ExchangeDetailsCardBinding) : RecyclerView.ViewHolder(binding.root){
                //val currencyView: TextView = binding.accountCurrency
                val balanceView: TextView = binding.accountBalance
                val tradingEnabledView: TextView = binding.exchangeName

                fun bind(account: CoinbaseProData){
                    //currencyView.text = account.currency
                    balanceView.text = account.balance
                    balanceView.setTextColor(
                        mainActivity.resources.getColor(
                            android.R.color.holo_orange_light,
                            mainActivity.theme
                        )
                    )

                    tradingEnabledView.text = mainActivity.resources.getString(
                        if(account.trading_enabled)
                            R.string.tradable
                        else
                            R.string.not_tradable
                    )
                }

        override fun toString(): String {
            return "ViewHolder(balanceView=${balanceView.text}, holdView=${tradingEnabledView.text})"
        }
    }
}