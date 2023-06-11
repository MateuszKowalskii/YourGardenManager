package com.example.yourgardenmanager

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class AddPlot : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plot)

        val addPlotButton = findViewById<Button>(R.id.addPlotButton)
        addPlotButton.setOnClickListener{
            val plotNameTextView = findViewById<EditText>(R.id.plotNameTextView)
            val plotName = plotNameTextView.text.toString()

            val plantNameTextView = findViewById<EditText>(R.id.plantNameTextView)
            val plotPlantType = plantNameTextView.text.toString()

            val areaSizeTextView = findViewById<EditText>(R.id.areaSizeTextView)
            val plotArea : Double = areaSizeTextView.text.toString().toDouble()

            if(plotName.isEmpty() or plotPlantType.isEmpty() or plotArea.isNaN()){
                val communique = findViewById<TextView>(R.id.communiqueTextView)
                communique.setText("Each field must be filled")
            }
            else{
                val nextPage = Intent(this, PlotsList::class.java).also {
                    it.putExtra("TYPE","add")
                    it.putExtra("NAME",plotName)
                    it.putExtra("PLANT",plotPlantType)
                    it.putExtra("AREA",plotArea)
                    startActivity(it)
                    finish()
                }
            }
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener{
            val nextPage = Intent(this, PlotsList::class.java)
            startActivity(nextPage)
            finish()
        }

    }
}