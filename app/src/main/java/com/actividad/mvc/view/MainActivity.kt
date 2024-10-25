package com.actividad.mvc.view

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.actividad.mvc.R
import com.actividad.mvc.model.Task
import com.actividad.mvc.model.TaskRepository

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskRepository = TaskRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encuentra las vistas usando findViewById
        val recyclerViewTasks = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTask)

        // Configura RecyclerView
        val tasks = taskRepository.getAllTasks().toMutableList() // Usa una lista mutable
        taskAdapter = TaskAdapter(
            tasks,
            onTaskCompleted = { task -> taskRepository.markTaskAsCompleted(task.id) },
            onDeleteTask = { task ->
                taskRepository.deleteTask(task.id)
                tasks.remove(task) // Eliminar de la lista
                taskAdapter.notifyDataSetChanged() // Actualizar el adaptador
            }
        )

        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter

        // Al hacer clic en el botón, abrir un diálogo para agregar tarea
        buttonAddTask.setOnClickListener {
            showAddTaskDialog(tasks)  // Pasar la lista mutable al diálogo
        }
    }

    // Función para mostrar el diálogo de agregar tarea
    private fun showAddTaskDialog(tasks: MutableList<Task>) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_task, null)
        dialogBuilder.setView(dialogView)

        val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = dialogView.findViewById<EditText>(R.id.editTextDescription)

        dialogBuilder.setTitle("Agregar Nueva Tarea")
        dialogBuilder.setPositiveButton("Agregar") { _, _ ->
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newTask = taskRepository.createTask(title, description)
                tasks.add(newTask) // Agregar la nueva tarea a la lista
                taskAdapter.notifyItemInserted(tasks.size - 1) // Notificar al adaptador
            }
        }
        dialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
