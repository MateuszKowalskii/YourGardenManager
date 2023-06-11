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

class PlannedActionsList : AppCompatActivity() {

    private lateinit var plansAdapter: PlansAdapter
    private lateinit var viewModel: PlanViewModel
    private lateinit var listOfPlans: LiveData<List<PlannedAction>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planned_actions_list)

        val adapter = PlansAdapter(mutableListOf())
        plansAdapter = adapter
        val editPage = android.content.Intent(this, EditPlan::class.java)
        /*adapter.setOnItemClickListener(object : PlansAdapter.onItemClickListener{

            override fun onItemClick(index: Int) {

                val plan = plansAdapter.getItem(index)
                val planId = plan.id
                val plotId = plan.plotId
                val planDescription = plan.description
                val planDate = plan.date


                editPage.also{
                    it.putExtra("PLANID",planId)
                    it.putExtra("PLOTID",plotId)
                    it.putExtra("DESCRIPTION",planDescription)
                    it.putExtra("DATE",planDate)
                    startActivity(it)
                    finish()
                }
            }

        })*/

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(PlanViewModel::class.java)

        val plansList = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.plansList)
        plansList.adapter = plansAdapter
        plansList.layoutManager = LinearLayoutManager(this)

        listOfPlans = viewModel.getPlans()
        listOfPlans.observe(this, Observer {
            if(it.isNotEmpty()){
                val adapter = PlansAdapter(it as MutableList<PlannedAction>)
                plansAdapter = adapter
                adapter.setOnItemClickListener(object : PlansAdapter.onItemClickListener{

                    override  fun onItemClick(index: Int) {
                        val plan = plansAdapter.getItem(index)
                        val planPlotId = plan.plotId
                        val planDate = plan.date
                        val planDescription = plan.description

                        editPage.also {
                            it.putExtra("PLANID",plan.id)
                            it.putExtra("PLOTID",planPlotId)
                            it.putExtra("DESCRIPTION",planDescription)
                            it.putExtra("DATE",planDate)
                            startActivity(it)
                            finish()
                        }

                    }
                })
                plansList.adapter = plansAdapter
            }
        })

        val goBackButton = findViewById<Button>(R.id.goBackButton)
        goBackButton.setOnClickListener{
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }
    }
}