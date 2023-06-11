package com.example.yourgardenmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class PlotsList : AppCompatActivity() {

    private lateinit var plotsAdapter: PlotsAdapter
    private lateinit var viewModel: PlotViewModel
    private lateinit var listOfPlots: LiveData<List<Plot>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plots_list)

        val adapter = PlotsAdapter(mutableListOf())
        plotsAdapter = adapter
        val editPage = Intent(this, EditPlot::class.java)
        adapter.setOnItemClickListener(object : PlotsAdapter.onItemClickListener{

            override fun onItemClick(index: Int) {
                val plot = plotsAdapter.getItem(index)
                val plotName = plot.name
                val plotPlantType = plot.plantType
                val plotArea = plot.area
                val plotID = plot.id

                editPage.also {
                    it.putExtra("ID",plotID)
                    it.putExtra("NAME",plotName)
                    it.putExtra("PLANT",plotPlantType)
                    it.putExtra("AREA",plotArea)
                    startActivity(it)
                    finish()
                }
            }
        })

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(PlotViewModel::class.java)

        //In case of receiving any messages
        val messageActionType = intent.getStringExtra("TYPE")
        val messagePlotName = intent.getStringExtra("NAME")
        val messagePlotPlantType = intent.getStringExtra("PLANT")
        val messagePlotArea : Double = intent.getDoubleExtra("AREA",0.0)

        if(messagePlotName != null && messagePlotPlantType != null && messagePlotArea != -10.0){
            if(messageActionType == "add"){
                val plot = Plot(0,messagePlotName,messagePlotPlantType,messagePlotArea)
                Toast.makeText(this@PlotsList,"Added plot "+ messagePlotName, Toast.LENGTH_SHORT).show()
                viewModel.insertPlot(plot)
                plotsAdapter.addPlot(plot)
            }
            else if(messageActionType == "edit"){
                val messagePlotId = intent.getIntExtra("ID",0)
                Toast.makeText(this@PlotsList,"Edited plot "+ messagePlotName, Toast.LENGTH_SHORT).show()
                val plot = Plot(messagePlotId,messagePlotName,messagePlotPlantType,messagePlotArea)
                viewModel.updatePlot(plot)
            }
            else if(messageActionType == "delete"){

                val messagePlotId = intent.getIntExtra("ID",0)

                Toast.makeText(this@PlotsList,"Deleted plot"+ plotsAdapter.itemCount, Toast.LENGTH_SHORT).show()
                val plot = Plot(messagePlotId,messagePlotName,messagePlotPlantType,messagePlotArea)
                plotsAdapter.deletePlot(plot)
                viewModel.deletePlot(plot)
            }
        }


        val plotsList = findViewById<RecyclerView>(R.id.plotsList)
        plotsList.adapter = plotsAdapter
        plotsList.layoutManager = LinearLayoutManager(this)

        listOfPlots = viewModel.getPlots()
        listOfPlots.observe(this, Observer {
            if(it.isNotEmpty()){
                val adapter = PlotsAdapter(it as MutableList<Plot>)
                plotsAdapter = adapter
                adapter.setOnItemClickListener(object : PlotsAdapter.onItemClickListener{

                    override fun onItemClick(index: Int) {
                        val plot = plotsAdapter.getItem(index)
                        val plotName = plot.name
                        val plotPlantType = plot.plantType
                        val plotArea = plot.area
                        val plotID = plot.id

                        editPage.also {
                            it.putExtra("ID",plotID)
                            it.putExtra("NAME",plotName)
                            it.putExtra("PLANT",plotPlantType)
                            it.putExtra("AREA",plotArea)
                            it.putExtra("INDEX",index)
                            startActivity(it)
                            finish()
                        }
                    }
                })
                plotsList.adapter = plotsAdapter
            }
        })


        val addPlotButton = findViewById<Button>(R.id.addPlotButton)
        addPlotButton.setOnClickListener{
            val nextPage = Intent(this, AddPlot::class.java)
            startActivity(nextPage)
            finish()
        }

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        goBackButton.setOnClickListener{
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }

    }
}