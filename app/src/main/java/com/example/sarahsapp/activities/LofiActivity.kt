package com.example.sarahsapp.activities

import android.os.Bundle
import android.widget.Toast
import com.example.sarahsapp.BuildConfig
import com.example.sarahsapp.databinding.LofiActivityLayoutBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class LofiActivity : YouTubeBaseActivity() {
    private lateinit var binding: LofiActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LofiActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.youtubePlayerView.initialize(BuildConfig.youtubeKey, onInitializedListener)
    }

    private val onInitializedListener = object : YouTubePlayer.OnInitializedListener {
        override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
                                             wasRestored: Boolean) {
            Toast.makeText(this@LofiActivity, "Initialized Youtube Player successfully", Toast.LENGTH_SHORT).show()

            if (!wasRestored) {
                try {
                    youTubePlayer?.loadVideo("5qap5aO4i9A")
                    youTubePlayer?.play()
                }catch (e: Exception){
                    Toast.makeText(this@LofiActivity, "Exception Occurred ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onInitializationFailure(provider: YouTubePlayer.Provider?, youTubeInitializationResult: YouTubeInitializationResult?) {
            val REQUEST_CODE = 0

            if (youTubeInitializationResult?.isUserRecoverableError == true) {
                youTubeInitializationResult.getErrorDialog(this@LofiActivity, REQUEST_CODE).show()
            } else {
                val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
                Toast.makeText(this@LofiActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }
}