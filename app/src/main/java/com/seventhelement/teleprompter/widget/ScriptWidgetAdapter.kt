package com.seventhelement.teleprompter.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import androidx.lifecycle.Observer
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script
import com.seventhelement.teleprompter.persistance.repository.ScriptRepository

class ScriptWidgetAdapter(private val context: Context) : RemoteViewsFactory {
    private var scriptsList: List<Script>? = null
    private var initLoad = true
    private fun populateScripts() {
        val repository = ScriptRepository(context)
        repository.getAllScripts()?.observeForever(object : Observer<List<Script?>?>{


            override fun onChanged(scripts: List<Script?>?) {
                scriptsList = scripts as List<Script>?
                if (initLoad) {
                    initLoad = false
                }
            }
        })
        while (initLoad) {
        }
    }

    override fun onCreate() {}
    override fun onDataSetChanged() {
        populateScripts()
    }

    override fun onDestroy() {}
    override fun getCount(): Int {
        return scriptsList!!.size
    }

    override fun getViewAt(i: Int): RemoteViews {
        val remoteView = RemoteViews(
            context.packageName, R.layout.script_remote_item
        )
        val script: Script = scriptsList!![i]
        remoteView.setTextViewText(
            R.id.timeRemote,
            TimeUtil.timeAgo(script.dateInMilli - 1000)
        )
        remoteView.setTextViewText(R.id.title, script.title)
        remoteView.setTextViewText(R.id.body, script.body)
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 3
    }

    override fun getItemId(i: Int): Long {
        return scriptsList!![i].id.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}
