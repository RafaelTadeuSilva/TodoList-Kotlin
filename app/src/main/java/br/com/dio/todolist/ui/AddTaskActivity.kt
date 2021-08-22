package br.com.dio.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.todolist.MyApp
import br.com.dio.todolist.R
import br.com.dio.todolist.databinding.ActivityAddTaskBinding
import br.com.dio.todolist.extensions.format
import br.com.dio.todolist.extensions.text
import br.com.dio.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            MyApp.taskDao?.findById(taskId)?.let {
                binding.tilTitulo.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
            binding.toolbar.title = getString(R.string.label_update)
            binding.btnNewTask.text = getString(R.string.label_update)
        }

        insertListeners()
    }

    private fun insertListeners() {

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = timePicker.minute.toString().padStart(2, '0')
                val hour = timePicker.hour.toString().padStart(2, '0')

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            if (!binding.tilTitulo.text.isNullOrBlank() && !binding.tilDate.text.isNullOrBlank()) {
                val task = Task(
                    title = binding.tilTitulo.text,
                    description = binding.tilDescription.text,
                    date = binding.tilDate.text,
                    hour = binding.tilHour.text,
                    id = intent.getIntExtra(TASK_ID, 0)
                )
                MyApp.taskDao?.insertTask(task)
                setResult(Activity.RESULT_OK)
                finish()
            }

        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}