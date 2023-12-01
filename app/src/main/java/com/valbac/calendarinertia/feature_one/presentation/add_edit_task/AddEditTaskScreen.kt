package com.valbac.calendarinertia.feature_one.presentation.add_edit_task

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import com.maxkeppeler.sheets.color.models.MultipleColors
import com.maxkeppeler.sheets.color.models.SingleColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddEditTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val calendarState = rememberUseCaseState()
    val clockState = rememberUseCaseState()
    val colorState = rememberUseCaseState()
    val pickedColor = remember { mutableStateOf(Color.Red.toArgb()) }
    val templateColors = MultipleColors.ColorsInt(
        Color.Yellow.toArgb(),
        Color.Green.toArgb(),
        Color.Cyan.toArgb(),
    )

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(AddEditTaskEvent.SetDate(date.dayOfMonth, date.monthValue, date.year))
            Log.d("SelectedDate", "${date.dayOfMonth}, ${date.monthValue}, ${date.year}")
        }
    )

    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutesSeconds { hour, minute, second ->
            viewModel.onEvent(AddEditTaskEvent.SetTime(hour,minute,second))
            Log.d("SelectedDate", "$hour:$minute:$second")
        }
    )

    ColorDialog(
        state = colorState,
        selection = ColorSelection(
            selectedColor = SingleColor(pickedColor.value),
            onSelectColor = { color ->
                viewModel.onEvent(AddEditTaskEvent.SetColor(color))
                Log.d("SelectedColor", "$color")
            },
        ),
        config = ColorConfig(
            templateColors = templateColors,
            defaultDisplayMode = ColorSelectionMode.TEMPLATE,
            allowCustomColorAlphaValues = false
        ),
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                    navigator.popBackStack()
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save note",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.value.title,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.SetTitle(it)) },
                label = { Text("Title") },
                placeholder = { Text("...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            OutlinedTextField(
                value = state.value.description,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.SetDescription(it)) },
                label = { Text("Description") },
                placeholder = { Text("...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(onClick = {
                calendarState.show()
            }) {
                Text(text = "Date Picker")
            }
            Button(onClick = {
                clockState.show()
            }) {
                Text(text = "Time Picker")
            }
            Button(onClick = {
                colorState.show()
            }) {
                Text(text = "Color Picker")
            }
        }
    }
}