package dev.tp_94.mobileapp.self_made_cake.presentation.components
import androidx.compose.foundation.background
import java.util.Calendar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(modifier: Modifier = Modifier,
                     shape: Shape = RoundedCornerShape(8.dp),
                     minDaysFromToday: Int = 1
                     ) {
    val calendar = remember(minDaysFromToday) {
        Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, minDaysFromToday + 1)
        }
    }

    var selectedDate by remember(minDaysFromToday) {
        mutableStateOf(Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, minDaysFromToday + 1)
        }.time)
    }
    var showDatePicker by remember { mutableStateOf(false) }

    val minDate = remember(minDaysFromToday) {
        Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, minDaysFromToday)
        }.time
    }

    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

    val buttonColors = ButtonColors(
        colorResource(R.color.light_background),
        contentColor = colorResource(R.color.dark_text),
        colorResource(R.color.light_background),
        disabledContentColor = colorResource(R.color.dark_text)
    )

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = colorResource(R.color.light_background),

        titleContentColor = colorResource(R.color.dark_text),
        headlineContentColor = colorResource(R.color.dark_text),

        weekdayContentColor = colorResource(R.color.light_text),
        subheadContentColor = colorResource(R.color.light_text),

        navigationContentColor = colorResource(R.color.middle_text),

        yearContentColor = colorResource(R.color.dark_text),
        disabledYearContentColor = colorResource(R.color.light_text),
        currentYearContentColor = colorResource(R.color.accent),
        selectedYearContentColor = colorResource(R.color.light_background),
        disabledSelectedYearContentColor = colorResource(R.color.light_text),

        selectedYearContainerColor = colorResource(R.color.accent),
        disabledSelectedYearContainerColor = colorResource(R.color.light_background),

        dayContentColor = colorResource(R.color.dark_text),
        disabledDayContentColor = colorResource(R.color.light_text),

        selectedDayContentColor = colorResource(R.color.light_background),
        disabledSelectedDayContentColor = colorResource(R.color.light_text),

        selectedDayContainerColor = colorResource(R.color.accent),
        disabledSelectedDayContainerColor = colorResource(R.color.middle_text),

        todayContentColor = colorResource(R.color.accent),
        todayDateBorderColor = colorResource(R.color.accent),

        dayInSelectionRangeContentColor = colorResource(R.color.light_background),
        dayInSelectionRangeContainerColor = colorResource(R.color.accent),

        dividerColor = colorResource(R.color.dark_text),
    )


    Column(modifier = modifier) {
        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
            colors = buttonColors,
            shape = shape,
        ) {

            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Срок выполнения: ${dateFormat.format(selectedDate)}",
                    style = TextStyles.regular(colorResource(R.color.dark_text)))
            }

            Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = null,
                tint = colorResource(R.color.dark_text),
                modifier = Modifier.padding(end = 8.dp)
            )

        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate.time,
                yearRange = IntRange(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.YEAR) + 1
                ),
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis >= minDate.time
                    }

                    override fun isSelectableYear(year: Int): Boolean {
                        return year >= calendar.get(Calendar.YEAR)
                    }
                }
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = Date(it)
                        }
                        showDatePicker = false
                    },
                        colors = buttonColors,
                        shape = shape) { Text("OK") }
                },
                colors = datePickerColors,
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = datePickerColors,
                    modifier = Modifier
                        .background(colorResource(R.color.light_background))
                        .wrapContentHeight(),
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewDatePickerButton() {
    DatePickerButton()
}