package br.com.dio.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.dio.todolist.MyApp
//import br.com.dio.todolist.MyApplication
import br.com.dio.todolist.databinding.ActivityMainBinding
import br.com.dio.todolist.repository.AppDatabase


class MainActivity : AppCompatActivity() {
//    val db = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java, "database-name"
//    ).build()

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

//    private var db: AppDatabase? = null
//    private var taskDao: TaskDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        io.reactivex.Observable.fromCallable {
            MyApp.db = AppDatabase.getAppDataBase(context = this)
            MyApp.taskDao =  MyApp.db?.taskDao()

//            var task1 = Task(title = "Male", date = "10", description = "test", hour = "5")
//            var task2 = Task(title = "Female", date = "10", description = "test", hour = "5")
//
//            with( MyApp.taskDao) {
//                this?.insertTask(task1)
//                this?.insertTask(task2)
//            }
//            MyApp.db?.taskDao()?.getAll()
//        }.doOnNext { list ->
//            var finalString = ""
//            list?.map { finalString += it.title + " - " }
//            tv_message.text = finalString
//
//        }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()

        binding.rvTask.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            MyApp.taskDao?.delete(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
//        io.reactivex.Observable.fromCallable {
            val list = MyApp.taskDao?.getAll()
            binding.includeEmpty.emptyState.visibility =
                if (list?.isEmpty() == true) View.VISIBLE else View.GONE
            adapter.submitList(list)
//        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}