package com.example.sarahsapp

import com.example.sarahsapp.ui.viewmodels.Friend
import com.example.sarahsapp.ui.viewmodels.MainViewModel
import com.example.sarahsapp.ui.viewmodels.SchoolCourse
import com.example.sarahsapp.ui.viewmodels.Student
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
// Defines a singleton of SchoolCourse
    single { SchoolCourse() }

    // Defines a factory - creates a new instance - new friend everytime
    factory { Friend() }

    factory { Student(get(), get()) }
}

val viewmodelModule = module {
    viewModel { MainViewModel(get(), get()) }
}
