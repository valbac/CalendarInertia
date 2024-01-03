package com.valbac.calendarinertia.feature_calendar.presentation.settings


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_calendar.presentation.destinations.CalendarMonthScreenDestination

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navigator.navigate(CalendarMonthScreenDestination) }) {
            Text(text = "Click Me")
        }
    }
}