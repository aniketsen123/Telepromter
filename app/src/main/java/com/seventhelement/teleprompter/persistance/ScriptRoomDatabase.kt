package com.seventhelement.teleprompter.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seventhelement.teleprompter.persistance.dao.ScriptDao
import com.seventhelement.teleprompter.persistance.entities.Script

@Database(entities = [Script::class], version = 1, exportSchema = false)
abstract class ScriptRoomDatabase : RoomDatabase() {
    abstract fun scriptDao(): ScriptDao?

    companion object {
        private var INSTANCE: ScriptRoomDatabase? = null
        fun getDatabase(context: Context): ScriptRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ScriptRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScriptRoomDatabase::class.java, "script_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}