package com.example.sarahsapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sarahsapp.BuildConfig
import com.example.sarahsapp.R
import com.example.sarahsapp.data.model.*
import com.example.sarahsapp.data.request.RequestBuilder
import com.example.sarahsapp.data.request.RequestHeader
import com.example.sarahsapp.databinding.ExchangeDetailsCardBinding
import com.example.sarahsapp.ui.adapters.CurrencyRecyclerViewAdapter
import com.example.sarahsapp.ui.utils.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
typealias OnCoinbaseDataSuccess = (CoinbaseData?) -> Unit

class CoinbaseFragment : Fragment(){
    private var _binding: ExchangeDetailsCardBinding? = null

    // Valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.open_exchange_details_transition_duration).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ExchangeDetailsCardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exchangeImage: ImageView = binding.exchangeImage
        val exchangeName: TextView = binding.exchangeName

        makeNetworkCall(createAPINetworkCall()){ coinbaseResponse ->
            if (coinbaseResponse != null){
                val values: List<Data> = coinbaseResponse.data.filter { s -> s.balance.amount.toFloat() > 0.00 }
                val currencyList = values.map { it.balance.toCurrencyData() }

                // todo: Fragment CoinbaseFragment not attached to a context, requireContext. invoke()
                binding.currencyRecyclerview.layoutManager = GridLayoutManager(requireContext(),1)
                binding.currencyRecyclerview.adapter = CurrencyRecyclerViewAdapter(currencyList)
            }
        }

        binding.navigationIcon.setOnClickListener{
            findNavController().navigateUp()
        }

        exchangeImage.setImageResource(R.drawable.coinbase)
        exchangeName.text = context?.getString(R.string.coinbase)
        // TODO: Add a case if not able to fetch data
//        else {
//            Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun Balance.toCurrencyData() = CurrencyData(
        name = currency,
        balance = amount
    )

    private fun createAPINetworkCall(): Call<CoinbaseData> {

        // Time stamp, creates a unique signature before calling, where security comes in handy
        val accessTimeStamp = "" + System.currentTimeMillis() / 1000L
        val destinationService = RequestBuilder.buildJsonRequest(RequestHeader::class.java, 1)

        // using cryptography hmac and SHA256, using the Key as a seed, preparing to hash the message
        // using it to 'sign' the message to come up with the accessSign
        val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        sha256_HMAC.init(secretKey)

        // Message signature, start with time stamp, concatinate with time stamp, http method, and request path
        val message = accessTimeStamp + accessType + accessPath

        // Sign the required message with the HMAC and Hex encode the result
        val accessSign: String = (sha256_HMAC.doFinal(message.toByteArray())).toHex()

        // Creating the API request with headers dynamically, since the signature needs a timestamp

        return destinationService.getCoinbaseHeaders(accessKey, accessSign, accessTimeStamp)
    }

    private fun makeNetworkCall(networkCallRequest : Call<CoinbaseData>, onSuccess: OnCoinbaseDataSuccess){
        networkCallRequest.enqueue(object : Callback<CoinbaseData> {
            override fun onResponse(call: Call<CoinbaseData>, response: Response<CoinbaseData>){
                if(response.isSuccessful){
                    val coinbaseData: CoinbaseData? = response.body()
                    onSuccess.invoke(coinbaseData)
                }
            }

            override fun onFailure(call: Call<CoinbaseData>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t $call", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    companion object {
        const val secret = BuildConfig.coinbaseSecret
        const val accessKey = BuildConfig.coinbaseKey

        // What type of API call we are making
        const val accessType = "GET"

        // We are retrieving User accounts
        const val accessPath = "/v2/accounts?limit=100"
    }
}