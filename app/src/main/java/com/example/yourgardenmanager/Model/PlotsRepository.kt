package com.example.yourgardenmanager

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class PlotsRepository(application: Application) {

    private var plotDao: PlotDao

    init{
        val database: PlotDatabase? = PlotDatabase
            .getInstance(application.applicationContext)

        plotDao = database!!.plotsDao()
    }

    fun insertPlot(plot: Plot): Job =
        CoroutineScope(Dispatchers.IO)
            .launch { plotDao.insertPlot(plot) }

    fun updatePlot(plot: Plot): Job =
        CoroutineScope(Dispatchers.IO)
            .launch { plotDao.updatePlot(plot) }

    fun deletePlot(plot: Plot): Job =
        CoroutineScope(Dispatchers.IO)
            .launch { plotDao.deletePlot(plot) }

    fun getAllPlotsAsync(): Deferred<LiveData<List<Plot>>> =
        CoroutineScope(Dispatchers.IO).async { plotDao.getPlots() }
}