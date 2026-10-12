package com.pes.studentorganizer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pes.studentorganizer.model.Assignment
import com.pes.studentorganizer.ui.components.AppBar
import com.pes.studentorganizer.ui.components.AssignmentForm
import java.util.UUID

@Composable
fun AddAssignmentScreen(
    onSaveAssignment: (Assignment) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Form state managed via remember and mutableStateOf
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Medium") }
    var description by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(false) }

    // Validation: Save button disabled until Title, Course, Due Date are populated
    val isFormValid = title.isNotBlank() && course.isNotBlank() && dueDate.isNotBlank()

    Scaffold(
        topBar = {
            AppBar(
                title = "Add New Assignment",
                canNavigateBack = true,
                onBackClick = onNavigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        // Form Container: LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 40.dp)
        ) {
            item {
                AssignmentForm(
                    title = title,
                    course = course,
                    dueDate = dueDate,
                    priority = priority,
                    description = description,
                    reminderEnabled = reminderEnabled,
                    onTitleChange = { title = it },
                    onCourseChange = { course = it },
                    onDueDateChange = { dueDate = it },
                    onPriorityChange = { priority = it },
                    onDescriptionChange = { description = it },
                    onReminderChange = { reminderEnabled = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))

                // Action Buttons: Row containing Cancel (OutlinedButton) and Save (Button)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        onClick = {
                            if (isFormValid) {
                                val newAssignment = Assignment(
                                    id = UUID.randomUUID().toString(),
                                    title = title.trim(),
                                    course = course.trim(),
                                    dueDate = dueDate.trim(),
                                    priority = priority,
                                    description = description.trim(),
                                    reminderEnabled = reminderEnabled,
                                    isCompleted = false
                                )
                                onSaveAssignment(newAssignment)
                                onNavigateBack()
                            }
                        },
                        enabled = isFormValid,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "Save Assignment",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
