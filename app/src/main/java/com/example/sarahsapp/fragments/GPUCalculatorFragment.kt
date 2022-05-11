package com.example.sarahsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.sarahsapp.databinding.GpuCalculatorFragmentBinding
import java.net.MalformedURLException
import java.net.URL
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

        var webContent = "<iframe src=\"https://widget.nicehash.com/profcalc\" width=\"400\" height=\"350\" scrolling=\"no\" id=\"nhiframe\"></iframe>"
        val calcWebView: WebView = binding.calculatorWebView

        val DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"

        calcWebView.settings.userAgentString = DESKTOP_USER_AGENT
        calcWebView.webChromeClient = WebChromeClient()
        calcWebView.webViewClient = WebViewClient()
        calcWebView.settings.javaScriptEnabled = true

        if (webContent.contains("iframe")){
            val matcher = Pattern.compile("src=\\\"([^\\\"]+)\\\"").matcher(webContent)
            matcher.find()

            val src = matcher.group(1)
            webContent = src

            try {
                val myURL = URL(src)
                calcWebView.loadUrl(src)
            } catch (e: MalformedURLException){
                e.printStackTrace()
            }
        } else {
            calcWebView.loadDataWithBaseURL(null,"<style>img{display: inline;height: auto;max-width: 100%;}</style>" + webContent, "text/html", "UTF-8", null)
        }

        return binding.root
    }
}