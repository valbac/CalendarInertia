package com.valbac.calendarinertia.feature_calendar.data.remote

import com.valbac.calendarinertia.feature_calendar.data.remote.dto.NextPublicHolidaysDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PublicHolidayApi {

    @GET("/api/v3/NextPublicHolidays/{countryCode}")
    suspend fun getPublicHolidaysInfo(
        @Path("countryCode") countryCode: String,
    ):List<NextPublicHolidaysDto>
}