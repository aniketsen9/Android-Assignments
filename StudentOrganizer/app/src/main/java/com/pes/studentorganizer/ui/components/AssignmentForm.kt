package com.pes.studentorganizer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AssignmentForm(
    title: String,
    course: String,
    dueDate: String,
    priority: String,
    description: String,
    reminderEnabled: Boolean,
    onTitleChange: (String) -> Unit,
    onCourseChange: (String) -> Unit,
    onDueDateChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onReminderChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val priorities = listOf("Low", "Medium", "High")
    val coursePresets = listOf("CS101", "MATH201", "ENG102", "PHYS105")

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Assignment Title (Required)
        Column {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Assignment Title *") },
                placeholder = { Text("e.g. Algorithms Problem Set 3") },
                singleLine = true,
                isError = title.isEmpty(),
                supportingText = {
                    if (title.isEmpty()) {
                        Text("Title is required", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 2. Course Name/Code
        Column {
            OutlinedTextField(
                value = course,
                onValueChange = onCourseChange,
                label = { Text("Course Name / Code *") },
                placeholder = { Text("e.g. CS101") },
                singleLine = true,
                isError = course.isEmpty(),
                supportingText = {
                    if (course.isEmpty()) {
                        Text("Course is required", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Quick preset course chips
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                coursePresets.forEach { preset ->
                    SuggestionChip(
                        onClick = { onCourseChange(preset) },
                        label = { Text(preset) }
                    )
                }
            }
        }

        // 3. Due Date
        Column {
            OutlinedTextField(
                value = dueDate,
                onValueChange = onDueDateChange,
                label = { Text("Due Date *") },
                placeholder = { Text("e.g. Oct 24, 2026") },
                singleLine = true,
                isError = dueDate.isEmpty(),
                trailingIcon = {
                    IconButton(onClick = { /* Trailing calendar icon */ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                supportingText = {
                    if (dueDate.isEmpty()) {
                        Text("Due date is required", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 4. Priority Level
        Column {
            Text(
                text = "Priority Level",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                priorities.forEach { p ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onPriorityChange(p) }
                    ) {
                        RadioButton(
                            selected = (priority.equals(p, ignoreCase = true)),
                            onClick = { onPriorityChange(p) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = p,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // 5. Description / Notes (Multi-line minLines=3, maxLines=5)
        Column {
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description / Notes") },
                placeholder = { Text("Add instructions, links, or submission requirements...") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 6. Reminder Setting (Checkbox)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onReminderChange(!reminderEnabled) }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reminderEnabled,
                onCheckedChange = onReminderChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Enable Reminder Notification",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
