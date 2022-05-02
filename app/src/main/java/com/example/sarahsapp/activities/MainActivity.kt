package com.example.sarahsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.ActivityMainBinding
import com.example.sarahsapp.ui.utils.contentView
import com.example.sarahsapp.ui.viewmodels.Student
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.run {
            findNavController(R.id.nav_host_fragment)
        }

        // Dependency Injection

        val student = get<Student>()
        student.beSmart()

        val student2 = get<Student>()
        student2.beSmart()

//        val viewModel = getViewModel<MainViewModel>()
//        viewModel.performAction()

    }

}