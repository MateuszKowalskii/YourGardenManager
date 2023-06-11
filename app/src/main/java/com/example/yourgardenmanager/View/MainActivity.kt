package com.example.yourgardenmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.yourgardenmanager.View.PlantsInformations

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val showPlotsButton = findViewById<Button>(R.id.showPlotsButton)
        showPlotsButton.setOnClickListener{
            val nextPage = Intent(this, PlotsList::class.java)
            startActivity(nextPage)
            finish()
        }

        val showPlansButton = findViewById<Button>(R.id.showPlansButton)
        showPlansButton.setOnClickListener{
            val nextPage = Intent(this, PlannedActionsList::class.java)
            startActivity(nextPage)
            finish()
        }

        val showPlantsButton = findViewById<Button>(R.id.showPlantsButton)
        showPlantsButton.setOnClickListener{
            val nextPage = Intent(this, PlantsInformations::class.java)
            startActivity(nextPage)
            finish()
        }

        val closeAppButton = findViewById<Button>(R.id.closeAppButton)
        closeAppButton.setOnClickListener{
            finishAffinity()
        }
    }
}