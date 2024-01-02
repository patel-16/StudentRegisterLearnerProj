package com.example.studentregister.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity -> Table of room database
// see how these annotations/decorators abstract away the complexity of
// making and managing tables

@Entity(tableName = "student_data_table")
data class Student(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id") // -> column info of a table
    var id: Int,
    @ColumnInfo(name = "student_name")
    var name: String,
    @ColumnInfo(name = "student_email")
    var email: String

)