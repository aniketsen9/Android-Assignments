package com.pes.studentorganizer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pes.studentorganizer.model.Assignment
import com.pes.studentorganizer.ui.components.AppBar
import com.pes.studentorganizer.ui.components.AssignmentCard

@Composable
fun AssignmentsScreen(
    assignments: List<Assignment>,
    onToggleComplete: (String) -> Unit,
    onNavigateToAddAssignment: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Pending", "Completed")

    val filteredAssignments = when (selectedFilter) {
        "Pending" -> assignments.filter { !it.isCompleted }
        "Completed" -> assignments.filter { it.isCompleted }
        else -> assignments
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "My Assignments",
                canNavigateBack = false,
                onBackClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddAssignment,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Assignment"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Filter Row: Horizontal row of filter RadioButtons ('All', 'Pending', 'Completed')
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                filters.forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { selectedFilter = filter }
                    ) {
                        RadioButton(
                            selected = (selectedFilter == filter),
                            onClick = { selectedFilter = filter },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedFilter == filter) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Content: LazyColumn containing interactive AssignmentCard components
            if (filteredAssignments.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No $selectedFilter assignments found.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredAssignments, key = { it.id }) { assignment ->
                        AssignmentCard(
                            title = assignment.title,
                            course = assignment.course,
                            dueDate = assignment.dueDate,
                            priority = assignment.priority,
                            isCompleted = assignment.isCompleted,
                            onToggleComplete = { onToggleComplete(assignment.id) }
                        )
                    }
                }
            }
        }
    }
}
