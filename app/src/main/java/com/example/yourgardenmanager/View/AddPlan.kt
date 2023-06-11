package com.example.yourgardenmanager

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.DatePicker
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast

class AddPlan : AppCompatActivity() {
    private lateinit var datePicker: DatePickerDialog
    private lateinit var viewModel: PlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)

        val messagePlotId = intent.getIntExtra("ID",0)
        val messagePlotName = intent.getStringExtra("NAME")
        if (messagePlotName != null) {
            val plotNameTextView = findViewById<TextView>(R.id.plotNameTextView)
            plotNameTextView.setText(messagePlotName)
        }

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(PlanViewModel::class.java)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val nextPage = Intent(this, PlotsList::class.java)
            startActivity(nextPage)
            finish()
        }

        val addPlanButton = findViewById<Button>(R.id.addPlanButton)
        addPlanButton.setOnClickListener {
            val descriptionTextView = findViewById<android.widget.TextView>(R.id.descriptionTextView)
            val datePicker = findViewById<DatePicker>(R.id.datePicker)

            val plannedDate = datePicker.getDayOfMonth().toString()+" "+(datePicker.getMonth()+1).toString()+" "+ datePicker.getYear().toString()

            val plan = PlannedAction(0,messagePlotId,descriptionTextView.text.toString(),plannedDate,false)
            viewModel.insertPlan(plan)
            Toast.makeText(this@AddPlan,"Added plan", Toast.LENGTH_SHORT).show()
        }

    }
}