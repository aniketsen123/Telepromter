package com.seventhelement.teleprompter.ui

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.RecyclerView
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script
import com.seventhelement.teleprompter.widget.TimeUtil

class ScriptAdapter: RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder>() {

    private var context: Context? = null
    private var scripts: List<Script>? = null
    private var listener: OnScriptSelectedListener? = null


    interface OnScriptSelectedListener {
        fun scriptSelected(script: Script)
    }

    fun setScripts(scripts: List<Script>) {
        this.scripts = scripts
        notifyDataSetChanged()
    }

    fun setListener(listener: OnScriptSelectedListener) {
        this.listener = listener
    }

    override fun getItemId(position: Int): Long {
        return scripts?.get(position)?.id?.toLong() ?: 0L
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.script_item, parent, false)
        val viewHolder = ScriptViewHolder(view)

        view.setOnClickListener {
            scripts?.get(viewHolder.adapterPosition)?.let { script ->
                listener?.scriptSelected(script)
               //Toast.makeText(context,script.title,Toast.LENGTH_SHORT).show()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ScriptViewHolder, position: Int) {
        scripts?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return scripts?.size ?: 0
    }

    inner class ScriptViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val timeView: TextView = view.findViewById(R.id.time)
        private val titleView: TextView = view.findViewById(R.id.title)
        private val bodyView: TextView = view.findViewById(R.id.body)

        fun bind(script: Script) {
            timeView.text = TimeUtil.timeAgo(script.dateInMilli)
            titleView.text = script.title
            bodyView.text = script.body
        }
    }
}