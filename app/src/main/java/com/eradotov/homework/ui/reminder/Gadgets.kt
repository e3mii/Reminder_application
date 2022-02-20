package com.eradotov.homework.ui.reminder

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class Gadgets(
    context: Context
) : ViewModel(){
    private val _state = MutableStateFlow(GadgetsViewState())
    val state: StateFlow<GadgetsViewState>
        get() = _state

    var context = context

    /*
     * method for calendar
     */
    fun DisplayCalendar(
    ){
        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int->
                _state.value = GadgetsViewState(date = "$dayOfMonth-${month+1}-$year")
            }, year, month, day
        )

        datePickerDialog.show()
    }
}

data class GadgetsViewState(
    val date: String = ""
)