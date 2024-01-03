package com.valbac.calendarinertia.feature_calendar.presentation.add_edit_task

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
import com.valbac.calendarinertia.core.AlarmItem
import com.valbac.calendarinertia.feature_calendar.data.AlarmSchedulerImpl
import java.time.LocalDateTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddEditTaskScreen(
    navigator: DestinationsNavigator,
    id: Int?,
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
    var checked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)

    }

    val scheduler = AlarmSchedulerImpl(context = context)
    var alarmItem: AlarmItem? = null

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(AddEditTaskEvent.SetDate(date.dayOfMonth, date.monthValue, date.year))
        }
    )

    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutesSeconds { hour, minute, second ->
            viewModel.onEvent(AddEditTaskEvent.SetTime(hour, minute, second))
        }
    )

    ColorDialog(
        state = colorState,
        selection = ColorSelection(
            selectedColor = SingleColor(pickedColor.value),
            onSelectColor = { color ->
                viewModel.onEvent(AddEditTaskEvent.SetColor(color))
            },
        ),
        config = ColorConfig(
            templateColors = templateColors,
            defaultDisplayMode = ColorSelectionMode.TEMPLATE,
            allowCustomColorAlphaValues = false
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navigator.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Menu"
                    )
                }
            },
                title = {
                    Text(
                        text = "Task"
                    )
                }
            )
        },
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
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
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
            Text(text = "Remind me?")
            Switch(
                checked = state.value.checked,
                onCheckedChange = {
                    if (!checked) {
                        if (!hasNotificationPermission) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else {
                            alarmItem = AlarmItem(
                                time = LocalDateTime.of(
                                    state.value.dateYear,
                                    state.value.dateMonth,
                                    state.value.dateDay,
                                    state.value.hour,
                                    state.value.minute,
                                    state.value.second
                                ),
                                message = state.value.title
                            )
                            alarmItem?.let(scheduler::schedule)
                        }
                    }
                    checked = it
                    viewModel.onEvent(AddEditTaskEvent.SetReminder(checked))
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
                enabled = state.value.hour in 0..23 && state.value.title != "" && state.value.dateYear != 0
            )
            IconButton(
                onClick = { alarmItem?.let(scheduler::cancel) },
            ) {
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = "Delete alarm"
                )
            }
        }
    }
}