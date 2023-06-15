package com.example.yourgardenmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Plot:: class],
    version = 2,
)
abstract class PlotDatabase: RoomDatabase() {
    //function to return Dao
    abstract fun plotsDao(): PlotDao

    //singleton pattern used here
    companion object {
        private var instance: PlotDatabase? = null

        fun getInstance(context: Context): PlotDatabase?{
            if(instance==null){
                instance = Room.databaseBuilder(
                    context,
                    PlotDatabase::class.java,
                    "plots_table")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstance(){
            instance = null
        }
    }
}