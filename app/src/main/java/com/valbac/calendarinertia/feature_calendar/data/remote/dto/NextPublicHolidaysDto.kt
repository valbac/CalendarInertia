package com.valbac.calendarinertia.feature_calendar.data.remote.dto


data class NextPublicHolidaysDto(
    val counties: List<String>,
    val countryCode: String,
    val date: String,
    val fixed: Boolean,
    val global: Boolean,
    val launchYear: Int?,
    val localName: String,
    val name: String,
    val types: List<String>
)