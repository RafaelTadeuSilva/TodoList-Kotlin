package br.com.dio.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String,
    val hour: String,
    val date: String
)