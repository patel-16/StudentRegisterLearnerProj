package com.example.studentregister

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDatabase

// xml -> view
// Activity -> view controller

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter

    // we need a record of which student object is selected
    // to perform update or delete operation
    // this selection shall help to fetch the primary key of the
    // student based on which the update or delete shall happen..
    private var selectedStudent: Student? = null

    // I guess makes sense to declare viewmodel as a class field
    // rather than inside some member function..
    private lateinit var viewModel: StudentViewModel

    // Remember the Activity lifecycle and where the OnCreate
    // stands and why specific setup code is written
    // in the overriden onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.etName)
        emailEditText = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)

        // note that this is "the" Recycler View which will
        // hold the view held by View Holder
        // it was declared in the View corresponding to the MainActivity only
        // that is activity_main.xml
        studentRecyclerView = findViewById(R.id.rvStudent)

        // getInstance -> singleton
        // application context -> for db to access android system i think
        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)

        // "lifecycle owner" -> this (activity)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        // lambda argument moved out of () here
        saveButton.setOnClickListener {
            if (selectedStudent != null) {
                updateStudentData()
                selectedStudent = null
                resetBtnText()
            }
            else {
                saveStudentData()
            }
            clearInput()
        }

        clearButton.setOnClickListener {
            if (selectedStudent != null) {
                deleteStudentData()
                selectedStudent = null
                resetBtnText()
            }
            clearInput()
        }

        initRecyclerView()

    }

    private fun saveStudentData() {
        viewModel.insertStudent(
            // this Id is just to fill the place
            // since for primary key, auto generate is enabled
            Student(
                0,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
    }

    private fun updateStudentData() {
        selectedStudent?.let {
            viewModel.updateStudent(
                Student(
                    it.id,
                    nameEditText.text.toString(),
                    emailEditText.text.toString()
                )
            )
        }
    }

    private fun deleteStudentData() {
        selectedStudent?.let {
            viewModel.deleteStudent(
                Student(
                    it.id,
                    nameEditText.text.toString(),
                    emailEditText.text.toString()
                )
            )
        }
    }

    private fun clearInput() {
        nameEditText.setText("")
        emailEditText.setText("")
    }

    private fun resetBtnText() {
        saveButton.text = "Save"
        clearButton.text = "Clear"
    }

    private fun initRecyclerView() {
        // Layout Manager is also provided by Recycler View
        // library, arranges elements in the List according to
        // docs, but don't know how exactly
        studentRecyclerView.layoutManager = LinearLayoutManager(this)


        // Need to see how a specific instance of student is pointed here
        // when the app is working...see the onBindViewHolder in the adapter
        // there we can see a specific student being bound to a view...
        studentRecyclerViewAdapter = StudentRecyclerViewAdapter({
            listItemClicked(it)
        })

        // I think we are sort of binding the recycler view
        // defined in the XML to its adapter,
        // to bind the view to its data ultimately I think
        studentRecyclerView.adapter = studentRecyclerViewAdapter

        displayStudentsList()
    }

    private fun displayStudentsList() {
        // viewModel gets this "students" list from StudentDAO
        // , specifically a function in the DAO with a SQL query
        viewModel.students.observe(this, {
            studentRecyclerViewAdapter.setList(it)
            // i guess needed to refresh the data displayed by list
            studentRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(student: Student) {
        selectedStudent = student
        saveButton.text = "Update"
        clearButton.text = "Delete"
        nameEditText.setText(student.name)
        emailEditText.setText(student.email)

        /*
        Toast.makeText(
            this,
            "Student Name is ${student.name}",
            Toast.LENGTH_LONG
        ).show()
         */
    }

}