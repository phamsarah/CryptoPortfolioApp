package com.example.sarahsapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.LofiActivityLayoutBinding
import com.example.sarahsapp.fragments.GPUCalculatorFragment
import com.google.android.material.navigation.NavigationBarView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class LofiActivity : AppCompatActivity() {
    private lateinit var binding: LofiActivityLayoutBinding

    private lateinit var youTubePlayerView: YouTubePlayerView

    private lateinit var bottomActionBar: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LofiActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomActionBar = binding.bottomNavigation

        youTubePlayerView = binding.youtubePlayerView
        initYouTubePlayerView()

        bottomActionBar.setOnItemSelectedListener { tab ->
            when(tab.itemId) {
                R.id.exchanges -> {
                    Toast.makeText(this, "Exchanges", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.calculator -> {
                    Toast.makeText(this, "Calculator", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, GPUCalculatorFragment() )
                    //findNavController(R.id.calculator).navigate()
                    true
                }
                else -> false
            }
        }

    }

    fun initYouTubePlayerView() {
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, "5qap5aO4i9A", 0f)
            }
        })
    }
}