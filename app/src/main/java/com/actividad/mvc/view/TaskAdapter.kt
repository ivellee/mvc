package com.actividad.mvc.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.actividad.mvc.R
import com.actividad.mvc.model.Task

class TaskAdapter(
    private val tasks: MutableList<Task>, // Cambiar a MutableList para poder modificar
    private val onTaskCompleted: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onTaskCompleted, onDeleteTask)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val completedCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)

        fun bind(task: Task, onTaskCompleted: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            completedCheckBox.isChecked = task.completed

            // Marcar tarea como completada
            completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                task.completed = isChecked
                onTaskCompleted(task)
            }

            // Eliminar tarea al hacer un clic largo
            itemView.setOnLongClickListener {
                showDeleteConfirmationDialog(task, onDeleteTask)
                true
            }
        }

        private fun showDeleteConfirmationDialog(task: Task, onDeleteTask: (Task) -> Unit) {
            AlertDialog.Builder(itemView.context)
                .setTitle("Eliminar Tarea")
                .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
                .setPositiveButton("Sí") { _, _ ->
                    onDeleteTask(task) // Eliminar tarea si se confirma
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
