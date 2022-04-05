package com.example.sarahsapp.fragments

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sarahsapp.BuildConfig
import com.example.sarahsapp.activities.MainActivity
import com.example.sarahsapp.data.model.Account
import com.example.sarahsapp.data.request.RequestBuilder
import com.example.sarahsapp.data.request.RequestHeader
import com.example.sarahsapp.databinding.FragmentPortfolioBinding
import com.example.sarahsapp.ui.adapters.AccountRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class PortfolioFragment : Fragment() {
    private var _binding: FragmentPortfolioBinding? = null

    // Valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO: this might be a problem later
        _binding = FragmentPortfolioBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeNetworkCall(createAPINetworkCall(), requireActivity() as MainActivity, binding.accountRecyclerView)
    }

    private fun createAPINetworkCall(): Call<List<Account>> {

        // Time stamp, creates a unique signature before calling, where security comes in handy
        val accessTimeStamp = "" + System.currentTimeMillis() / 1000L
        val destinationService = RequestBuilder.buildJsonRequest(RequestHeader::class.java)

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
        val accessSign: String =
            Base64.encodeToString(sha256_HMAC.doFinal(message.toByteArray()), Base64.NO_WRAP)

        // Creating the API request with headers dynamically, since the signature needs a timestamp

        return destinationService.accounts(
            accessKey,
            accessPassphrase,
            accessSign,
            accessTimeStamp
        )
    }

    private fun makeNetworkCall(networkCallRequest : Call<List<Account>>, mainActivity: MainActivity, recyclerView: RecyclerView){
        networkCallRequest.enqueue(object : Callback<List<Account>> {
            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                if(response.isSuccessful){
                    val accountList: List<Account>? = response.body()
                    if (accountList != null){
                        recyclerView.layoutManager = GridLayoutManager(context, 2)
                        recyclerView.adapter = AccountRecyclerViewAdapter(mainActivity, accountList.filter { s -> s.balance.toFloat() > 0.00})
                    }
                } else {
                    Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t $call", Toast.LENGTH_SHORT).show()
            }

        })
    }

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