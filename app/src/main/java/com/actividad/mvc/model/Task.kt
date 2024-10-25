package com.actividad.mvc.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var completed: Boolean = false
)
