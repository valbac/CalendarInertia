package com.valbac.calendarinertia.feature_calendar.data.repository

import com.valbac.calendarinertia.feature_calendar.data.remote.PublicHolidayApi
import com.valbac.calendarinertia.feature_calendar.data.remote.dto.NextPublicHolidaysDto
import com.valbac.calendarinertia.feature_calendar.domain.repository.PublicHolidaysRepository
import javax.inject.Inject

class PublicHolidaysRepositoryImpl @Inject constructor(
    private val api: PublicHolidayApi
): PublicHolidaysRepository{
    override suspend fun getPublicHolidaysInfo(countryCode: String): List<NextPublicHolidaysDto> {
        return api.getPublicHolidaysInfo(countryCode)
    }
}