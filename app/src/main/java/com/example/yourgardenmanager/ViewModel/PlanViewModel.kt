package com.example.yourgardenmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class PlanViewModel(application: Application):
    AndroidViewModel(application){

    private var plansRepository: PlansRepository =
        PlansRepository(application)

    private var allPlans: Deferred<LiveData<List<PlannedAction>>> =
        plansRepository.getAllPlansAsync()

    fun insertPlan(plan: PlannedAction){
        plansRepository.insertPlan(plan)
    }

    fun updatePlan(plan: PlannedAction){
        plansRepository.updatePlan(plan)
    }

    fun deletePlan(plan: PlannedAction){
        plansRepository.deletePlan(plan)
    }

    fun getPlans(): LiveData<List<PlannedAction>> = runBlocking{
        allPlans.await()
    }
}