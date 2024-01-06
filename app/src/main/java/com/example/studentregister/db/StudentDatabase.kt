package com.example.studentregister.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://stackoverflow.com/questions/45616548/kotlin-why-use-abstract-classes-vs-interfaces

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    // https://stackoverflow.com/questions/391483/what-is-the-difference-between-an-abstract-method-and-a-virtual-method
    // I do not know why is it not renamed to getStudentDao? Currently just following the tutorial...
    // dao -> data access object (actually it is an interface, again don't know why)
    abstract fun studentDao(): StudentDao

    // something of kind of static members, belonging to the class
    companion object {

        // Volatile allows for writes to be immeditely visible to other threads
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        // I guess context would be need since context
        // allows for communication of program with the Android System
        // since the database could be stored in the device/System storage..

        fun getInstance(context: Context): StudentDatabase {

            // synchronized -> to avoid concurrent execution by multiple threads
            // by holding the monitior
            // ref: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-synchronized/

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).build()
                }

                return instance
            }
        }
    }

}