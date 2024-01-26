package com.example.studentregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student

// Ref: https://developer.android.com/develop/ui/views/layout/recyclerview
// Recycler View-> To display dynamic lists
// I think it does it in a efficient manner

// this adapter has the methods to bind data to view (held by ViewHolder I guess)
class StudentRecyclerViewAdapter(
    private val listElementClickListener: (Student) -> Unit
): RecyclerView.Adapter<StudentViewHolder>() {

    private val studentList = ArrayList<Student>()

    // from the looks of it it appears this function is destined to
    // provide the View Holder its view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        // also the LayoutInflater is used to get a View Object from corresponding XML file
        val layoutInflater = LayoutInflater.from(parent.context)

        //list_item aka list_element (the one to go in ViewHolder)
        val listItem = layoutInflater.inflate(
            R.layout.student_recycler_view_list_item,
            parent,
            false
        )

        return StudentViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    // this function appears to receive View Holder (holder) from
    // onCreateViewHolder
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], listElementClickListener)
    }

    fun setList(students: List<Student>) {
        studentList.clear()
        studentList.addAll(students)
    }

}

// each element in recycler view list is defined by ViewHolder
// see the View type parameter being passed as constructor (as the name suggests, it holds a view)
// (it will hold the view we created in student_recycler_view_list_item.xml
// hence tvName and tvEmail)
class StudentViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    // for the onClickListener for any element in the list,
    // a lambda is passed as a parameter (lambda -> function without a name)
    fun bind(student: Student, listElementClickListener: (Student) -> Unit) {
        val nameTextView = view.findViewById<TextView>(R.id.tvName)
        val emailTextView = view.findViewById<TextView>(R.id.tvEmail)
        nameTextView.text = student.name
        emailTextView.text = student.email
        // view is the list element, so setting listener on it
        // I guess lambda arguments can be moved out of parenthesis
        // but I am going to keep it anyways, want to find out where this
        // "it:View!" comes from
        view.setOnClickListener(
            {
                listElementClickListener(student)
            }
        )
    }
}