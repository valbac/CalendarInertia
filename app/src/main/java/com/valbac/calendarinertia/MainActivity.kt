package com.valbac.calendarinertia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.valbac.calendarinertia.feature_calendar.presentation.NavGraphs
import com.valbac.calendarinertia.ui.theme.CalendarInertiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarInertiaTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}