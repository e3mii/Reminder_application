package com.eradotov.homework.data.entity

import androidx.room.*
import java.security.Timestamp
import java.sql.Date
import java.sql.Time
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true),
        Index("user_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val rId: Long = 0,
    @ColumnInfo(name = "user_id") val rUserId: Long,
    @ColumnInfo(name = "rMessage") val rMessage: String,
    @ColumnInfo(name = "rLocationX") val rLocationX: Double,
    @ColumnInfo(name = "rLocationY") val rLocationY: Double,
    @ColumnInfo(name = "rTime") val rTime: Long,
    @ColumnInfo(name = "rCreationTime") val rCreationTime: Long,
    @ColumnInfo(name = "reminderSeen") val rSeen: Boolean?,
    @ColumnInfo(name = "toNotify") val toNotify: Boolean
)
