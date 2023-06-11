package com.example.yourgardenmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import android.widget.DatePicker
import android.widget.Toast

class EditPlan : AppCompatActivity() {

    private lateinit var viewModel: PlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_plan)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(PlanViewModel::class.java)

        //Receiving a message form PlannedActionsList
        val messagePlanDescription = intent.getStringExtra("DESCRIPTION")
        val messagePlanRealisationDate = intent.getStringExtra("DATE")
        val messagePlanId = intent.getIntExtra("PLANID",0)
        val messagePlotId = intent.getIntExtra("PLOTID",0)

        val planDescriptionTextView = findViewById<EditText>(R.id.planDescriptionTextView)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        if(messagePlanDescription != null){

            planDescriptionTextView.setText(messagePlanDescription)

        }

        val editPlanButton = findViewById<Button>(R.id.editPlanButton)
        editPlanButton.setOnClickListener{
            if(messagePlanDescription != null && messagePlanRealisationDate != null) {
                var planDate = datePicker.getDayOfMonth().toString()+" "+(datePicker.getMonth()+1).toString()+" "+ datePicker.getYear().toString()
                val planToUpdate = PlannedAction(
                    messagePlanId,
                    messagePlotId,
                    planDescriptionTextView.text.toString(),
                    planDate,
                    false
                )
                viewModel.updatePlan(planToUpdate)
                Toast.makeText(this@EditPlan,"Edited plan", Toast.LENGTH_SHORT).show()
                val nextPage = Intent(this, PlannedActionsList::class.java)
                startActivity(nextPage)
                finish()
            }
        }

        val deletePlanButton = findViewById<Button>(R.id.deletePlanButton)
        deletePlanButton.setOnClickListener{
            if(messagePlanDescription != null && messagePlanRealisationDate != null) {
                val planToDelete = PlannedAction(
                    messagePlanId,
                    messagePlotId,
                    messagePlanDescription,
                    messagePlanRealisationDate,
                    false
                )
                viewModel.deletePlan(planToDelete)
                Toast.makeText(this@EditPlan,"Deleted plan", Toast.LENGTH_SHORT).show()
                val nextPage = Intent(this, PlannedActionsList::class.java)
                startActivity(nextPage)
                finish()
            }
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener{
            val nextPage = Intent(this, PlannedActionsList::class.java)
            startActivity(nextPage)
            finish()
        }
    }
}