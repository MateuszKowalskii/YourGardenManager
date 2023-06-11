package com.example.yourgardenmanager

import java.util.Date
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity dedicated to store data about planned actions on plots

@Entity(tableName = "plans_table")
data class PlannedAction (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo (name = "plotId")
    val plotId:Int,
    @ColumnInfo (name = "description")
    val description: String,
    @ColumnInfo (name = "date")
    val date: String,
    @ColumnInfo (name = "isDone")
    val isDone: Boolean = false
    )
