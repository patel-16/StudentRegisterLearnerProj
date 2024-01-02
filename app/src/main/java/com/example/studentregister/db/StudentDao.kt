package com.example.studentregister.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO -> Data access object
// Annotations/Decorators are important in this case, not function names

// some of these annotated functions can return id of inserted row,
// or say number of deleted rows etc and stuff...

// insert, update, delete do not require sql queries, I guess auto generated,
// but not for query operation


@Dao
interface StudentDao {

    // from google -> https://www.baeldung.com/kotlin/coroutines
    /*
    * The suspend keyword means that this function can be blocking.
    * Such a function can suspend a buildSequence coroutine.
    * Suspending functions can be created as standard Kotlin functions,
    * but we need to be aware that we can only call them from within a coroutine.
    * Otherwise, we'll get a compiler error.
    * */

    @Insert
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    // no suspend keyword needed since it will have its own coroutines
    // see how we use Live Data as a data type (maybe we can say of Observable type)
    @Query("SELECT * FROM student_data_table")
    fun getAllStudents():LiveData<List<Student>>

}