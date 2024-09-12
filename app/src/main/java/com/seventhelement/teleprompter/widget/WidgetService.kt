package com.seventhelement.teleprompter.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ScriptWidgetAdapter(this.applicationContext)
    }
}