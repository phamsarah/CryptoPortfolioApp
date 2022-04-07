package com.example.sarahsapp.ui.viewmodels

import androidx.lifecycle.ViewModel

class Student(
    val course: SchoolCourse = SchoolCourse(),
    val friend: Friend = Friend()){

    fun beSmart() {
        course.study()
        friend.hangout()
    }
}

class SchoolCourse {
    fun study(){
        println("I am studying")
    }
}

class Friend {
    fun hangout(){
        println("We're hanging out")
    }
}

class MainViewModel(val course: SchoolCourse, val friend: Friend) : ViewModel() {
    fun performAction() {

    }
}


