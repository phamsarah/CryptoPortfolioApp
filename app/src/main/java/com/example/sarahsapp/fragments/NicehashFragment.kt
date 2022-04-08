package com.example.sarahsapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sarahsapp.BuildConfig
import com.example.sarahsapp.R
import com.example.sarahsapp.data.model.NicehashData
import com.example.sarahsapp.data.model.NicehashTime
import com.example.sarahsapp.data.request.RequestBuilder
import com.example.sarahsapp.data.request.RequestHeader
import com.example.sarahsapp.databinding.ExchangeDetailsCardBinding
import com.example.sarahsapp.ui.utils.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
typealias OnNicehashDataSuccess = (NicehashData?) -> Unit

class NicehashFragment: Fragment() {
    private var _binding: ExchangeDetailsCardBinding? = null
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

        val destinationService = RequestBuilder.buildJsonRequest(RequestHeader::class.java, 2)
        getServerTime(destinationService)

        binding.navigationIcon.setOnClickListener{
            findNavController().navigateUp()
        }

        exchangeImage.setImageResource(R.drawable.nicehash)
        exchangeName.text = view.context.getString(R.string.nicehash)
    }

    private fun createAPINetworkCall(destinationService: RequestHeader, serverTime: String): Call<NicehashData> {
        // Time stamp, creates a unique signature before calling, where security comes in handy
//        val accessTimeStamp = "" + System.currentTimeMillis()

        // Nonce is a random string
        val nonce = UUID.randomUUID().toString()

        // Input is a byte array composed of ordered fields using zero byte as a separator using ISO-8859-1 encoding
        //TODO: Put null for Query to see what would happen
        val input = listOf(secret, accessKey, serverTime, nonce, null, orgId, null, accessType, accessPath)
            .map { it?.toByteArray(Charsets.ISO_8859_1) }
        val testInput = listOf(secret, accessKey, "1649437311001", "p1agy0rqtypdl530blpbbo0c6zj3d98u", null, orgId, null, accessType, accessPath)
            .map { it?.toByteArray(Charsets.ISO_8859_1) }

        val digest = signInputByHmacSha256(testInput)
        val auth = "$accessKey:$digest"

        // Creating the API request with headers dynamically, since the signature needs a timestamp
        return destinationService.getNicehashHeaders(
            serverTime,
            nonce,
            auth,
            orgId
        )
    }

    private fun getServerTime(destinationService: RequestHeader) {
        destinationService.getNicehashTime().enqueue(object : Callback<NicehashTime>{
            override fun onResponse(call: Call<NicehashTime>, response: Response<NicehashTime>) {
                if(response.isSuccessful){
                    val serverTime: NicehashTime? = response.body()
                    makeCalltoAccounts(destinationService, serverTime!!.serverTime.toString())

                } else { Toast.makeText(context, R.string.time_error, Toast.LENGTH_SHORT).show() }
            }

            override fun onFailure(call: Call<NicehashTime>, t: Throwable) { Toast.makeText(context, R.string.time_error, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun makeCalltoAccounts(destinationService: RequestHeader, serverTime: String){
        makeNetworkCall(createAPINetworkCall(destinationService, serverTime)){ nicehashResponse ->
            if(nicehashResponse != null){
                binding.currencyRecyclerview.layoutManager = GridLayoutManager(context, 1)

//                val currencies = coinbaseProResponse.filter { s -> s.balance.toFloat() > 0.00 }
//                val currencyList = currencies.map { it.toCurrencyData() }
//                binding.currencyRecyclerview.adapter = CurrencyRecyclerViewAdapter(currencyList)
            }
        }
    }


    private fun signInputByHmacSha256(segments: List<ByteArray?>): String? {
        return try {
            val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")

            sha256_HMAC.init(secretKey)

            var first = true
            for (segment in segments) {
                if (!first) {
                    sha256_HMAC.update(0.toByte())
                } else {
                    first = false
                }
                if (segment != null) {
                    sha256_HMAC.update(segment)
                }
            }

            sha256_HMAC.doFinal().toHex()

        } catch (e: Exception) {
            throw RuntimeException("Cannot create HmacSHA256", e)
        }
    }

    private fun makeNetworkCall(networkCallRequest : Call<NicehashData>, onSuccess: OnNicehashDataSuccess){
        networkCallRequest.enqueue(object : Callback<NicehashData> {
            override fun onResponse(call: Call<NicehashData>, response: Response<NicehashData>) {
                if (response.isSuccessful) {
                    val nicehashData: NicehashData? = response.body()
                    onSuccess.invoke(nicehashData)
                } else { Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show() }
            }

            override fun onFailure(call: Call<NicehashData>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t $call", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    companion object {
        const val secret = BuildConfig.nicehashSecret
        const val accessKey = BuildConfig.nicehashKey
        const val orgId = BuildConfig.nicehashOrgId

        // What type of API call we are making
        const val accessType = "GET"

        // We are retrieving User accounts
        const val accessPath = "/main/api/v2/accounting/accounts2"

        const val query="algorithm=X16R&page=0&size=100"
    }
}

