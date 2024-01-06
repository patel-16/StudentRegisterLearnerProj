package com.example.studentregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDao
import kotlinx.coroutines.launch

// I guess we can name it as MainActivityViewModel or something as well
// from our experience...

// I think this app does not follow a MVVM...
// or maybe not the Repository pattern

// Here we will call all 4 functions in the dao
// so as to connect the view controller (i.e. Activity)
// with the Data side of the software

// I think we can call this dependency injection of some type,
// since we are passing a dependency as a parameter
// Source: https://developer.android.com/training/dependency-injection

// Also Since this View model has constructor parameter
// ViewModelFactory will be needed
// If not constructor parameter, then ViewModelFactory is also not required
// Source : https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories

class StudentViewModel(private val dao: StudentDao): ViewModel() {

    // Queries are handled by coroutine provide by room itself
    val students = dao.getAllStudents()

    // viewModelScope.launch -> coroutine to do the DB operations
    fun updateStudent(student: Student) = viewModelScope.launch {
        dao.updateStudent(student)
    }

    fun insertStudent(student: Student) = viewModelScope.launch {
        dao.insertStudent(student)
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        dao.deleteStudent(student)
    }

}