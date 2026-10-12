package com.pes.studentorganizer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pes.studentorganizer.model.Assignment
import com.pes.studentorganizer.model.Course
import com.pes.studentorganizer.ui.components.AppBar
import com.pes.studentorganizer.ui.components.AssignmentCard
import com.pes.studentorganizer.ui.components.CourseInfo

@Composable
fun DashboardScreen(
    studentName: String = "Aniket Sen",
    courses: List<Course>,
    assignments: List<Assignment>,
    onToggleComplete: (String) -> Unit,
    onNavigateToAddAssignment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pendingTasksCount = assignments.count { !it.isCompleted }
    val activeCoursesCount = courses.size

    // Urgent deadlines: top 2 pending assignments, or fallback to first 2
    val urgentDeadlines = assignments.filter { !it.isCompleted }.take(2).ifEmpty {
        assignments.take(2)
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "Welcome back, $studentName!",
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
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp) // extra padding for bottom bar & FAB
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Quick Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Active Courses",
                    count = "$activeCoursesCount",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Pending Tasks",
                    count = "$pendingTasksCount",
                    icon = Icons.Default.PendingActions,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Active Courses Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Active Courses",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${courses.size} enrolled",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Active Courses Section (LazyRow)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(courses) { course ->
                    CourseInfo(
                        courseName = course.courseName,
                        courseCode = course.courseCode,
                        instructor = course.instructor,
                        progress = course.progress
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Urgent Deadlines Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Urgent Deadlines",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Top upcoming",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Urgent Deadlines Section (Column showing top 2 upcoming items)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (urgentDeadlines.isEmpty()) {
                    Text(
                        text = "No upcoming deadlines. Great job!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                } else {
                    urgentDeadlines.forEach { assignment ->
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

@Composable
private fun StatCard(
    title: String,
    count: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = count,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
