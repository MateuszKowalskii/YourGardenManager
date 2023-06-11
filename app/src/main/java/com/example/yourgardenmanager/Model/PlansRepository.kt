package com.example.yourgardenmanager

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class PlansRepository (application: Application){

    private var planDao: PlanDao

    init{
        val database: PlanDatabase? = PlanDatabase
            .getInstance(application.applicationContext)

        planDao = database!!.plansDao()
    }

    fun insertPlan(plan: PlannedAction): Job =
        CoroutineScope(Dispatchers.IO)
            .launch{planDao.insertPlan(plan)}

    fun updatePlan(plan: PlannedAction): Job =
        CoroutineScope(Dispatchers.IO)
            .launch{planDao.updatePlan(plan)}

    fun deletePlan(plan: PlannedAction): Job =
        CoroutineScope(Dispatchers.IO)
            .launch { planDao.deletePlan(plan) }

    fun getAllPlansAsync(): Deferred<LiveData<List<PlannedAction>>> =
        CoroutineScope(Dispatchers.IO).async { planDao.getPlans() }
}