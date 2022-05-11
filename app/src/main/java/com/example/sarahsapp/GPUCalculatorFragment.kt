package com.example.sarahsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        var webContent = "https://widget.nicehash.com/profcalc"
        var calcWebView = binding.calculatorWebView

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

        // Inflate the layout for this fragment
        _binding = GpuCalculatorFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
}