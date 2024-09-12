package com.seventhelement.teleprompter.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.seventhelement.teleprompter.persistance.entities.Script
import com.seventhelement.teleprompter.persistance.repository.ScriptRepository

class ScriptViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ScriptRepository = ScriptRepository(application)
    private val allScripts: LiveData<List<Script?>?>? = repository.getAllScripts()

    fun getAllScripts(): LiveData<List<Script?>?>? {
        return allScripts
    }

    fun saveNewScript(script: Script) {
        repository.insertScript(script)
    }

    fun getScript(id: Int): Script? {
        return repository.getScript(id)
    }
}