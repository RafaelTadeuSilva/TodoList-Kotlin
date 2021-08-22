package br.com.dio.todolist.datasource

import androidx.room.*
import br.com.dio.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun delete(task: Task)
}