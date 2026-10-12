package com.pes.studentorganizer.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pes.studentorganizer.model.Assignment
import com.pes.studentorganizer.model.Course
import com.pes.studentorganizer.ui.components.BottomBar
import com.pes.studentorganizer.ui.screens.AddAssignmentScreen
import com.pes.studentorganizer.ui.screens.AssignmentsScreen
import com.pes.studentorganizer.ui.screens.DashboardScreen
import com.pes.studentorganizer.ui.screens.ProfileScreen
import com.pes.studentorganizer.ui.screens.SettingsScreen
import com.pes.studentorganizer.ui.theme.StudentOrganizerTheme

@Composable
fun StudentOrganizerApp() {
    // Shared state management
    var isDarkMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var defaultStartScreen by remember { mutableStateOf("Dashboard") }

    // Sample Courses
    val courses = remember {
        mutableStateListOf(
            Course("Introduction to Computer Science", "CS101", "Prof. Alan Turing", 0.75f),
            Course("Linear Algebra & Applications", "MATH201", "Prof. Katherine Johnson", 0.60f),
            Course("Academic Writing & Research", "ENG102", "Prof. Maya Angelou", 0.90f),
            Course("Physics: Mechanics & Waves", "PHYS105", "Prof. Richard Feynman", 0.45f)
        )
    }

    // Sample Assignments (7 pending to match "Pending Tasks (7)")
    val assignments = remember {
        mutableStateListOf(
            Assignment(
                id = "1",
                title = "Algorithms Problem Set 3",
                course = "CS101",
                dueDate = "Oct 15, 2026",
                priority = "High",
                description = "Implement and benchmark divide-and-conquer sorting algorithms.",
                isCompleted = false
            ),
            Assignment(
                id = "2",
                title = "Eigenvalues Matrix Proofs",
                course = "MATH201",
                dueDate = "Oct 18, 2026",
                priority = "High",
                description = "Complete exercises 4.1 to 4.7 in textbook chapter 4.",
                isCompleted = false
            ),
            Assignment(
                id = "3",
                title = "Argumentative Essay Draft",
                course = "ENG102",
                dueDate = "Oct 22, 2026",
                priority = "Medium",
                description = "First draft of 1500 words with 5 peer-reviewed citations.",
                isCompleted = false
            ),
            Assignment(
                id = "4",
                title = "Rotational Dynamics Lab Report",
                course = "PHYS105",
                dueDate = "Oct 25, 2026",
                priority = "Medium",
                description = "Analyze experimental pendulum dataset and plot error bars.",
                isCompleted = false
            ),
            Assignment(
                id = "5",
                title = "Object-Oriented Design Quiz",
                course = "CS101",
                dueDate = "Oct 28, 2026",
                priority = "Low",
                description = "Review SOLID principles and design patterns.",
                isCompleted = false
            ),
            Assignment(
                id = "6",
                title = "Data Structures Traversal",
                course = "CS101",
                dueDate = "Oct 29, 2026",
                priority = "Medium",
                description = "Implement balanced AVL tree insertions.",
                isCompleted = false
            ),
            Assignment(
                id = "7",
                title = "Friction & Energy HW",
                course = "PHYS105",
                dueDate = "Nov 02, 2026",
                priority = "Low",
                description = "Solve chapter 6 review exercises.",
                isCompleted = false
            ),
            Assignment(
                id = "8",
                title = "Calculus Diagnostic Test",
                course = "MATH201",
                dueDate = "Oct 05, 2026",
                priority = "Medium",
                description = "Matrix determinants and vector transformations.",
                isCompleted = true
            ),
            Assignment(
                id = "9",
                title = "Grammar Workshop Review",
                course = "ENG102",
                dueDate = "Oct 08, 2026",
                priority = "Low",
                description = "Peer review for team assignment.",
                isCompleted = true
            )
        )
    }

    val toggleComplete: (String) -> Unit = { id ->
        val index = assignments.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = assignments[index]
            assignments[index] = current.copy(isCompleted = !current.isCompleted)
        }
    }

    StudentOrganizerTheme(darkTheme = isDarkMode) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Task 6: Conditionally hide BottomBar when on add_assignment screen
        val showBottomBar = currentRoute != Screen.AddAssignment.route

        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut()
                ) {
                    BottomBar(
                        navController = navController,
                        currentRoute = currentRoute
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            val startDestination = if (defaultStartScreen == "Assignments") {
                Screen.Assignments.route
            } else {
                Screen.Dashboard.route
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Route 1: dashboard
                composable(Screen.Dashboard.route) {
                    DashboardScreen(
                        studentName = "Aniket Sen",
                        courses = courses,
                        assignments = assignments,
                        onToggleComplete = toggleComplete,
                        onNavigateToAddAssignment = {
                            navController.navigate(Screen.AddAssignment.route)
                        }
                    )
                }

                // Route 2: assignments
                composable(Screen.Assignments.route) {
                    AssignmentsScreen(
                        assignments = assignments,
                        onToggleComplete = toggleComplete,
                        onNavigateToAddAssignment = {
                            navController.navigate(Screen.AddAssignment.route)
                        }
                    )
                }

                // Route 3: profile
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        studentName = "Aniket Sen",
                        studentId = "STU-2026-8842",
                        major = "Computer Science & AI",
                        gpa = "3.82",
                        completedCredits = "84",
                        currentSemester = "Fall 2026"
                    )
                }

                // Route 4: settings
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { isDarkMode = it },
                        notificationsEnabled = notificationsEnabled,
                        onNotificationsChange = { notificationsEnabled = it },
                        defaultStartScreen = defaultStartScreen,
                        onDefaultStartScreenChange = { defaultStartScreen = it }
                    )
                }

                // Route 5: add_assignment
                composable(Screen.AddAssignment.route) {
                    AddAssignmentScreen(
                        onSaveAssignment = { newAssignment ->
                            assignments.add(0, newAssignment)
                        },
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
