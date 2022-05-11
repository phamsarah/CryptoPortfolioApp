package com.example.sarahsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.ActivityMainBinding
import com.example.sarahsapp.fragments.GPUCalculatorFragment
import com.example.sarahsapp.ui.utils.contentView
import com.example.sarahsapp.ui.viewmodels.Student
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    private lateinit var youTubePlayerView: YouTubePlayerView

    private  lateinit var bottomActionBar: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up Bottom Navigation
        binding.run {
            findNavController(R.id.nav_host_fragment)
        }

        val navHostFragment = supportFragmentManager.findFragmentById( R.id.nav_host_fragment) as NavHostFragment
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)


        // Dependency Injection

        val student = get<Student>()
        student.beSmart()

        val student2 = get<Student>()
        student2.beSmart()

//        val viewModel = getViewModel<MainViewModel>()
//        viewModel.performAction()

        bottomActionBar = binding.bottomNavigation

        youTubePlayerView = binding.youtubePlayerView
        initYouTubePlayerView()

    }

    private fun initYouTubePlayerView() {
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, "5qap5aO4i9A", 0f)
            }
        })
    }

}