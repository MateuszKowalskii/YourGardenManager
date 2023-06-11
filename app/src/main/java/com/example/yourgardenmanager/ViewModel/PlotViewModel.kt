package com.example.yourgardenmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class PlotViewModel(application: Application):
    AndroidViewModel(application) {

    private var plotsRepository: PlotsRepository =
        PlotsRepository(application)

    private var allPlots: Deferred<LiveData<List<Plot>>> =
        plotsRepository.getAllPlotsAsync()

    fun insertPlot(plot: Plot){
        plotsRepository.insertPlot(plot)
    }

    fun updatePlot(plot: Plot){
        plotsRepository.updatePlot(plot)
    }

    fun deletePlot(plot: Plot){
        plotsRepository.deletePlot(plot)
    }

    fun getPlots(): LiveData<List<Plot>> = runBlocking{
        allPlots.await()
    }
}
