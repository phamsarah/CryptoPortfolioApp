package com.example.sarahsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.BuildConfig
import com.example.sarahsapp.activities.MainActivity
import com.example.sarahsapp.data.model.CoinbaseAccount
import com.example.sarahsapp.data.request.RequestBuilder
import com.example.sarahsapp.data.request.RequestHeader
import com.example.sarahsapp.databinding.FragmentPortfolioBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class CoinbaseFragment : Fragment() {
    private var _binding: FragmentPortfolioBinding? = null

    // Valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPortfolioBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeNetworkCall(createAPINetworkCall(), requireActivity() as MainActivity, binding.accountRecyclerView)
    }

    private fun createAPINetworkCall(): Call<CoinbaseAccount> {

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

    private fun makeNetworkCall(networkCallRequest : Call<CoinbaseAccount>, mainActivity: MainActivity, recyclerView: RecyclerView){
        networkCallRequest.enqueue(object : Callback<CoinbaseAccount> {
            override fun onResponse(call: Call<CoinbaseAccount>, response: Response<CoinbaseAccount>) {
                if(response.isSuccessful){
                    val accountList: CoinbaseAccount? = response.body()
                    if (accountList != null){
                        recyclerView.layoutManager = GridLayoutManager(context, 2)

                        Log.d("coinbaseoutput", accountList.toString())
                        //recyclerView.adapter = AccountRecyclerViewAdapter(mainActivity, accountList.filter { s -> s.balance.toFloat() > 0.00})
                    }
                } else {
                    Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoinbaseAccount>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t $call", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    companion object {
        const val secret = BuildConfig.coinbaseSecret
        const val accessKey = BuildConfig.coinbaseKey

        // What type of API call we are making
        const val accessType = "GET"

        // We are retrieving User accounts
        const val accessPath = "/v2/accounts"
    }
}