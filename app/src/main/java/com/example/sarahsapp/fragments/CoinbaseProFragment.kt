package com.example.sarahsapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Base64
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
import com.example.sarahsapp.data.model.CoinbaseProData
import com.example.sarahsapp.data.model.CurrencyData
import com.example.sarahsapp.data.request.RequestBuilder
import com.example.sarahsapp.data.request.RequestHeader
import com.example.sarahsapp.databinding.ExchangeDetailsCardBinding
import com.example.sarahsapp.ui.adapters.CurrencyRecyclerViewAdapter
import com.example.sarahsapp.ui.utils.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
typealias OnCoinbaseProDataSuccess = (List<CoinbaseProData>?) -> Unit

class CoinbaseProFragment : Fragment() {
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

        makeNetworkCall(createAPINetworkCall()) { coinbaseProResponse ->
            if (coinbaseProResponse != null) {
                val currencies = coinbaseProResponse.filter { s -> s.balance.toFloat() > 0.00 }
                val currencyList = currencies.map { it.toCurrencyData() }

                binding.currencyRecyclerview.layoutManager = GridLayoutManager(context, 1)
                binding.currencyRecyclerview.adapter = CurrencyRecyclerViewAdapter(currencyList)
            }
        }

        binding.navigationIcon.setOnClickListener{
            findNavController().navigateUp()
        }

        exchangeImage.setImageResource(R.drawable.coinbasepro)
        exchangeName.text = view.context.getString(R.string.coinbase_pro)

        // TODO: Add a case if not able to fetch data
    //        } else {
//            Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun createAPINetworkCall(): Call<List<CoinbaseProData>> {

        // Time stamp, creates a unique signature before calling, where security comes in handy
        val accessTimeStamp = "" + System.currentTimeMillis() / 1000L
        val destinationService = RequestBuilder.buildJsonRequest(RequestHeader::class.java, 0)

        // must decode the base64 version of the API secret, put it into byte array form
        val secretDecoded: ByteArray = Base64.decode(secret, Base64.NO_WRAP)

        // using cryptography hmac and SHA256, using the Key as a seed, preparing to hash the message
        // using it to 'sign' the message to come up with the accessSign
        val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secretDecoded, "HmacSHA256")
        sha256_HMAC.init(secretKey)

        // Message signature, start with time stamp, concatinate with time stamp, http method, and request path
        val message = accessTimeStamp + accessType + accessPath

        // Sign the required message with the HMAC and base64 encode the result
        val accessSign: String = Base64.encodeToString(sha256_HMAC.doFinal(message.toByteArray()), Base64.NO_WRAP)

        // Creating the API request with headers dynamically, since the signature needs a timestamp

        return destinationService.getCoinbaseProHeaders(
            accessKey,
            accessPassphrase,
            accessSign,
            accessTimeStamp
        )
    }

    private fun makeNetworkCall(networkCallRequest : Call<List<CoinbaseProData>>, onSuccess: OnCoinbaseProDataSuccess){
        networkCallRequest.enqueue(object : Callback<List<CoinbaseProData>> {
            override fun onResponse(call: Call<List<CoinbaseProData>>, response: Response<List<CoinbaseProData>>) {
                if (response.isSuccessful) {
                    val coinbaseProData: List<CoinbaseProData>? = response.body()
                    onSuccess.invoke(coinbaseProData)
                }
            }

            override fun onFailure(call: Call<List<CoinbaseProData>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t $call", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun CoinbaseProData.toCurrencyData() = CurrencyData(
        name = currency,
        balance = balance
    )

    companion object {
        const val secret = BuildConfig.secret
        const val accessKey = BuildConfig.key
        const val accessPassphrase = BuildConfig.passPhrase

        // What type of API call we are making
        const val accessType = "GET"

        // We are retrieving User accounts
        const val accessPath = "/accounts"
    }
}