package com.pes.studentorganizer

import com.pes.studentorganizer.model.Assignment
import com.pes.studentorganizer.model.Course
import com.pes.studentorganizer.ui.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StudentOrganizerTest {

    @Test
    fun testScreenRoutes() {
        assertEquals("dashboard", Screen.Dashboard.route)
        assertEquals("assignments", Screen.Assignments.route)
        assertEquals("profile", Screen.Profile.route)
        assertEquals("settings", Screen.Settings.route)
        assertEquals("add_assignment", Screen.AddAssignment.route)
    }

    @Test
    fun testCourseModel() {
        val course = Course(
            courseName = "Introduction to Computer Science",
            courseCode = "CS101",
            instructor = "Prof. Alan Turing",
            progress = 0.75f
        )
        assertEquals("CS101", course.courseCode)
        assertEquals(0.75f, course.progress, 0.001f)
    }

    @Test
    fun testAssignmentToggleCompletion() {
        val assignment = Assignment(
            id = "1",
            title = "Algorithms Problem Set 3",
            course = "CS101",
            dueDate = "Oct 15, 2026",
            priority = "High",
            isCompleted = false
        )
        assertFalse(assignment.isCompleted)
        val toggled = assignment.copy(isCompleted = !assignment.isCompleted)
        assertTrue(toggled.isCompleted)
    }

    @Test
    fun testFormValidationRule() {
        // Validation rule requires Title, Course, and Due Date to be non-blank
        fun validate(title: String, course: String, dueDate: String): Boolean {
            return title.isNotBlank() && course.isNotBlank() && dueDate.isNotBlank()
        }

        assertFalse(validate("", "CS101", "Oct 24, 2026"))
        assertFalse(validate("Title", "", "Oct 24, 2026"))
        assertFalse(validate("Title", "CS101", ""))
        assertTrue(validate("Title", "CS101", "Oct 24, 2026"))
    }
}
