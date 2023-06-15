package com.example.yourgardenmanager

import androidx.lifecycle.LiveData
import androidx.room.*

//Data access object interface dedicated to work with Plot objects

@Dao
interface PlotDao {
    @Insert
    fun insertPlot(plot: Plot)

    @Delete
    fun deletePlot(plot: Plot)

    @Update
    fun updatePlot(plot: Plot)

    @Query("SELECT * FROM plots_table ORDER BY name")
    fun getPlots() : LiveData<List<Plot>>
}