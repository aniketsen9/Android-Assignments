package com.pes.studentorganizer.model

data class Assignment(
    val id: String,
    val title: String,
    val course: String,
    val dueDate: String,
    val priority: String, // "High", "Medium", "Low"
    val description: String = "",
    val isCompleted: Boolean = false,
    val reminderEnabled: Boolean = false
)
