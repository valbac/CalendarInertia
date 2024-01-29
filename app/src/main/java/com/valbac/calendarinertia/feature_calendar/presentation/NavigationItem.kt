package com.valbac.calendarinertia.feature_calendar.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FreeCancellation
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FreeCancellation
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

val items = listOf(
    NavigationItem(
        title = "Calendar",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange
    ),
    NavigationItem(
        title = "Tasks",
        selectedIcon = Icons.Filled.Task,
        unselectedIcon = Icons.Outlined.Task
    ),
    NavigationItem(
        title = "Public Holidays",
        selectedIcon = Icons.Filled.FreeCancellation,
        unselectedIcon = Icons.Outlined.FreeCancellation
    )
)
