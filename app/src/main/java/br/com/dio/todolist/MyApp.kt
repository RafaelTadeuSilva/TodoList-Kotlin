package br.com.dio.todolist

import android.app.Application
import br.com.dio.todolist.repository.AppDatabase
import br.com.dio.todolist.datasource.TaskDao

class MyApp : Application() {
    companion object {
        var db: AppDatabase? = null
        var taskDao: TaskDao? = null
    }
}