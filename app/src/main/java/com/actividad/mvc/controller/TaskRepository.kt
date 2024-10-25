package com.actividad.mvc.controller

import com.actividad.mvc.model.Task

object TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun getAllTasks(): List<Task> {
        // Simular obtención de datos de una base de datos
        // En un caso real, se obtendrían los datos de una base de datos local o remota
        return tasks.toList()
    }

    fun addTask(task: Task) {
        tasks.add(task)
        // Notificar a la vista que los datos han cambiado
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
        // Notificar a la vista que los datos han cambiado
    }
}