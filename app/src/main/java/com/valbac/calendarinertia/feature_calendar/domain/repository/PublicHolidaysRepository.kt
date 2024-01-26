package com.valbac.calendarinertia.feature_calendar.domain.repository

import com.valbac.calendarinertia.feature_calendar.data.remote.dto.NextPublicHolidaysDto

interface PublicHolidaysRepository {
    suspend fun getPublicHolidaysInfo(countryCode: String): List<NextPublicHolidaysDto>
}