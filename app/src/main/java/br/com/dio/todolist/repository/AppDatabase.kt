package br.com.dio.todolist.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.todolist.datasource.TaskDao
import br.com.dio.todolist.model.Task

@Database(version = 1, entities = [Task::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "myDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}