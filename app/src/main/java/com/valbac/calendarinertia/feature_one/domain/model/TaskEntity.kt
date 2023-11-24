package com.valbac.calendarinertia.feature_one.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    val color: Int,
    val dateDay: Int,
    val dateMonth: Int,
    val dateYear: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
