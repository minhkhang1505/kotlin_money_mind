package com.nguyenminhkhang.moneymind.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {
    var weeklySpending by mutableStateOf(listOf(100f, // Monday
        150f, // Tuesday
        200f, // Wednesday
        170f, // Thursday
        120f, // Friday
        90f,  // Saturday
        180f  // Sunday
    ))

    fun updateWeeklySpending(newSpending: List<Float>) {
        weeklySpending = newSpending
    }

}