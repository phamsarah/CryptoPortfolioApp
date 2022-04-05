package com.example.sarahsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.sarahsapp.R
import com.example.sarahsapp.ui.viewmodels.Student
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHostFragment.navController

        // Dependency Injection

        val student = get<Student>()
        student.beSmart()

        val student2 = get<Student>()
        student2.beSmart()

//        val viewModel = getViewModel<MainViewModel>()
//        viewModel.performAction()

        // TODO: only if we want a actionbar
        //setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}