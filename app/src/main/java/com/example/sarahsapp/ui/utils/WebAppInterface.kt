package com.example.sarahsapp.ui.utils

import android.webkit.JavascriptInterface
import org.json.JSONObject

public class WebAppInterface {
    @JavascriptInterface
    fun handleMyEvent(jsonString: String){
        val data: JSONObject = JSONObject(jsonString)
        // do stuff
    }
}