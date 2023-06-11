package com.example.yourgardenmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditPlot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_plot)

        //Receiving a message form PlotsList
        val messagePlotName = intent.getStringExtra("NAME")
        val messagePlotPlantType = intent.getStringExtra("PLANT")
        val messagePlotArea : Double = intent.getDoubleExtra("AREA",0.0)
        val messagePlotId = intent.getIntExtra("ID",0)

        val plotNameField = findViewById<EditText>(R.id.plotNameTextView)
        val plotPlantTypeField = findViewById<EditText>(R.id.plantNameTextView)
        val plotAreaField = findViewById<EditText>(R.id.areaSizeTextView)

        if(messagePlotName != null && messagePlotPlantType != null && messagePlotArea != -10.0){

            plotNameField.setText(messagePlotName)
            plotPlantTypeField.setText(messagePlotPlantType)
            plotAreaField .setText(messagePlotArea.toString())

        }

        val editButton = findViewById<Button>(R.id.editPlotButton)
        editButton.setOnClickListener{
            val plotName = plotNameField.text.toString()
            val plotPlantType = plotPlantTypeField.text.toString()
            val plotArea : Double = plotAreaField.text.toString().toDouble()

            if(plotName.isEmpty() or plotPlantType.isEmpty() or plotArea.isNaN()){
                Toast.makeText(this@EditPlot,"Each field must be filled", Toast.LENGTH_SHORT).show()
            }
            else {

                val nextPage = Intent(this, PlotsList::class.java).also {
                    it.putExtra("TYPE","edit")
                    it.putExtra("NAME", plotName)
                    it.putExtra("PLANT", plotPlantType)
                    it.putExtra("AREA", plotArea)
                    it.putExtra("ID",messagePlotId)
                    startActivity(it)
                    finish()
                }
            }
        }

        val deleteButton = findViewById<Button>(R.id.deletePlotButton)
        deleteButton.setOnClickListener{
            val nextPage  = Intent(this, PlotsList::class.java).also {
                it.putExtra("TYPE","delete")
                it.putExtra("NAME", messagePlotName)
                it.putExtra("PLANT", messagePlotPlantType)
                it.putExtra("AREA", messagePlotArea)
                it.putExtra("ID",messagePlotId)
                startActivity(it)
                finish()
            }
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener{
            val nextPage = Intent(this, PlotsList::class.java)
            startActivity(nextPage)
            finish()
        }

        val planActionButton = findViewById<Button>(R.id.planActionButton)
        planActionButton.setOnClickListener{
            val nextPage = Intent(this, AddPlan::class.java).also{
                it.putExtra("ID",messagePlotId)
                it.putExtra("NAME",messagePlotName)
            }
            startActivity(nextPage)
            finish()
        }
    }
}