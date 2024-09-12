package com.seventhelement.teleprompter.persistance.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.seventhelement.teleprompter.persistance.entities.Script


@Dao
interface ScriptDao {
    @Insert(onConflict = REPLACE)
    fun insert(script: Script?)

    @Query("DELETE FROM script")
    fun deleteAll()

    @Query("SELECT * from script ORDER BY date_created_milliseconds ASC")
    fun getAllScripts(): LiveData<List<Script?>?>?

    @Query("SELECT * from script WHERE id = :id")
    fun getScript(id: Int): Script?
}
