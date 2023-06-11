package com.example.yourgardenmanager

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert

//Data access object interface dedicated to work with Plan objects

@androidx.room.Dao
interface PlanDao{
    @Insert
    fun insertPlan(plan: PlannedAction)

    @Delete
    fun deletePlan(plan: PlannedAction)

    @Update
    fun updatePlan(plan: PlannedAction)

    @Query("SELECT * FROM plans_table ORDER BY date")
    fun getPlans(): LiveData<List<PlannedAction>>
}

