package com.enzoroiz.basicdatabinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var student = Student(1, "Enzo Doe", "enzodoe@gmail.com")
    private val studentMutableLiveData = MutableLiveData<Student>()
    val studentLiveData: LiveData<Student>
        get() = studentMutableLiveData

    val studentClassLiveData = MutableLiveData<String>()

    init {
        studentMutableLiveData.value = student
        studentClassLiveData.value = "Android Architecture"
    }

    fun updateStudent() {
        student = if (studentLiveData.value?.id == 2L) {
            Student(1, "Enzo Doe", "enzodoe@gmail.com")
        } else {
            Student(2, "Enzo John", "enzojohn@gmail.com")
        }

        studentMutableLiveData.value = student
    }
}