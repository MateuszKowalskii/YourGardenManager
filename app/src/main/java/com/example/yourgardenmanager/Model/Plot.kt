package com.example.yourgardenmanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity dedicated to store data about plots

@Entity(tableName = "plots_table")
data class Plot (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo (name = "name")
    val name: String,
    @ColumnInfo(name = "plantType")
    val plantType: String,
    @ColumnInfo(name = "area")
    val area: Double,
    )