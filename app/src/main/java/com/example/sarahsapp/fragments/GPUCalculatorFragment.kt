package com.example.sarahsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.GpuCalculatorFragmentBinding
import java.net.MalformedURLException
import java.util.regex.Pattern


class GPUCalculatorFragment : Fragment() {
    private var _binding: GpuCalculatorFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = GpuCalculatorFragmentBinding.inflate(inflater,container,false)

        val webContent = "<iframe src=\"https://widget.nicehash.com/profcalc\" width=\"400\" height=\"350\" scrolling=\"no\" id=\"nhiframe\"></iframe>"
        val calcWebView: WebView = binding.calculatorWebView

        calcWebView.webChromeClient = WebChromeClient()
        calcWebView.webViewClient = WebViewClient()
        calcWebView.settings.javaScriptEnabled = true

        if (webContent.contains("iframe")){
            val matcher = Pattern.compile("src=\\\"([^\\\"]+)\\\"").matcher(webContent)
            matcher.find()

            try {
                calcWebView.loadUrl(matcher.group(1)!!)
            } catch (e: MalformedURLException){
                Toast.makeText(activity, R.string.gpu_fragment_error, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        return binding.root
    }
}