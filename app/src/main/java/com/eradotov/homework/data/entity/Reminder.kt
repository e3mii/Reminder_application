package com.eradotov.homework.data.entity

import java.util.*

data class Reminder(
    val rId: Long,
    val rTitle: String,
    val rContent: String,
    val rDate: Date?,
)
