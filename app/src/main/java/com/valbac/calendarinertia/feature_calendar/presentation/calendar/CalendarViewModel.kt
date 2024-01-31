package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import androidx.lifecycle.ViewModel
import com.valbac.calendarinertia.feature_calendar.domain.repository.PublicHolidaysRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: PublicHolidaysRepository
) : ViewModel() {

    fun getPublicHolidaysDEFlow() = flow {
        var retryCount = 0
        while (true) {
            try {
                val holidays = withContext(Dispatchers.IO) {
                    repository.getPublicHolidaysInfo("DE")
                }
                emit(holidays)
                break
            } catch (e: Exception) {
                retryCount++
                if (retryCount >= MAX_RETRIES) {
                    break
                }
            }
        }
    }

    companion object {
        const val MAX_RETRIES = 3
    }
}
