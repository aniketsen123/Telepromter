package com.seventhelement.teleprompter.persistance.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.seventhelement.teleprompter.persistance.ScriptRoomDatabase
import com.seventhelement.teleprompter.persistance.dao.ScriptDao
import com.seventhelement.teleprompter.persistance.entities.Script


class ScriptRepository(context: Context) {

    private val mDao: ScriptDao? = ScriptRoomDatabase.getDatabase(context)?.scriptDao()

    fun insertScript(script: Script) {
        mDao?.let { ScriptAsync().setDao(it).setScript(script).execute(ScriptOperation.WRITE_SCRIPT) }
    }

    fun getScript(id: Int): Script? {
        val async = mDao?.let { ScriptAsync().setDao(it).setId(id) }
        async?.execute(ScriptOperation.READ_SCRIPT)
        return async?.getScript()
    }

    fun getAllScripts(): LiveData<List<Script?>?>? {
        return mDao?.getAllScripts()
    }

    fun deleteAll() {
        mDao?.deleteAll()
    }

    private inner class ScriptAsync : AsyncTask<ScriptOperation, Void, Void>() {

        private var dao: ScriptDao? = null
        private var id: Int = 0
        private var script: Script? = null

        fun setId(id: Int): ScriptAsync {
            this.id = id
            return this
        }

        fun setDao(dao: ScriptDao): ScriptAsync {
            this.dao = dao
            return this
        }

        fun setScript(script: Script): ScriptAsync {
            this.script = script
            return this
        }

        fun getScript(): Script? {
            return script
        }

        override fun doInBackground(vararg scriptOperations: ScriptOperation): Void? {
            when (scriptOperations[0]) {
                ScriptOperation.READ_SCRIPT -> script = dao?.getScript(id)
                ScriptOperation.WRITE_SCRIPT -> script?.let { dao?.insert(it) }
            }
            return null
        }
    }

    private enum class ScriptOperation {
        READ_SCRIPT,
        WRITE_SCRIPT
    }
}