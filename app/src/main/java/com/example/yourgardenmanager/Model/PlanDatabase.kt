package com.example.yourgardenmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [PlannedAction:: class],
    version = 1,
)
abstract class PlanDatabase: RoomDatabase() {

    //function to return Dao
    abstract fun plansDao(): PlanDao

    //singleton pattern used here
    companion object {
        private var instance: PlanDatabase? = null
        

        fun getInstance(context: android.content.Context): PlanDatabase? {
            if (instance == null) {
                instance = androidx.room.Room.databaseBuilder(
                    context,
                    PlanDatabase::class.java,
                    "plans_table"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstance() {
            instance = null
        }
    }
}
