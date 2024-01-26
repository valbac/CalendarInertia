package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import androidx.lifecycle.ViewModel
import com.valbac.calendarinertia.feature_calendar.domain.repository.PublicHolidaysRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: PublicHolidaysRepository
) : ViewModel() {

    val publicHolidaysDEFlow = flow {
        emit(repository.getPublicHolidaysInfo("DE"))
    }


}