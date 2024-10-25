package com.actividad.mvc.model

class TaskRepository {

    // Lista para almacenar las tareas
    private val tasks = mutableListOf<Task>()
    private var currentId = 1

    // Método para crear una nueva tarea
    fun createTask(title: String, description: String): Task {
        val newTask = Task(id = currentId++, title = title, description = description)
        tasks.add(newTask)
        return newTask
    }

    // Método para obtener todas las tareas
    fun getAllTasks(): List<Task> {
        return tasks
    }

    // Método para marcar una tarea como completada
    fun markTaskAsCompleted(taskId: Int): Boolean {
        val task = tasks.find { it.id == taskId }
        return if (task != null) {
            task.completed = true
            true
        } else {
            false
        }
    }

    // Método para eliminar una tarea
    fun deleteTask(taskId: Int): Boolean {
        return tasks.removeIf { it.id == taskId }
    }
}
