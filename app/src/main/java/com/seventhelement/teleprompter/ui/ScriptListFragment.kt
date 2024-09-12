package com.seventhelement.teleprompter.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script
import com.seventhelement.teleprompter.viewmodel.ScriptViewModel
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ScriptListFragment : Fragment() {
    private var viewModel: ScriptViewModel? = null
    private var activity: MainActivity? = null
    private var listener: OnAddScriptListener? = null

    interface OnAddScriptListener {
        fun createNewScript(scriptBody: String?)
    }


    var addNew: FloatingActionButton? = null


    var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_script_list, container, false)
        recyclerView=view.findViewById(R.id.rv_scripts)
        addNew=view.findViewById(R.id.add_new)

        activity = getActivity() as MainActivity?
        activity?.supportActionBar!!.setTitle(R.string.app_tile)
        viewModel = ViewModelProvider(this).get(ScriptViewModel::class.java)
        val adapter = ScriptAdapter()
        viewModel!!.getAllScripts()!!.observe(
            viewLifecycleOwner
        ) { scripts ->
            adapter.setScripts(scripts as List<Script>)
            val activity = getActivity() as MainActivity?
            adapter.setListener(activity!!)
            recyclerView!!.layoutManager = LinearLayoutManager(context)
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.adapter = adapter
        }
        addNew!!.setOnClickListener { showOptions() }
        val activity = getActivity() as MainActivity?
        adapter.setListener(activity!!)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.adapter = adapter
        return view
    }

    fun setListener(listener: OnAddScriptListener?) {
        this.listener = listener
    }

    private fun showOptions() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.options_menu)
        val upload: Button? = dialog.findViewById(R.id.upload)
        val create: Button? = dialog.findViewById(R.id.create)
        upload?.setOnClickListener {
            dialog.dismiss()
            browseFile()
        }
        create?.setOnClickListener {
            dialog.dismiss()
            listener!!.createNewScript(null)
        }
        dialog.show()
    }

    private fun browseFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/*"
        startActivityForResult(intent, CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dataText: String
        val uri: Uri?
        if (requestCode == CODE) {
            if (data != null) {
                uri = data.data
                try {
                    dataText = readTextFromUri(uri)
                    listener!!.createNewScript(dataText)
                    Log.d(FRAGMENT_TAG, dataText)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri?): String {
        val inputStream = requireActivity().contentResolver.openInputStream(
            uri!!
        )
        val reader = BufferedReader(
            InputStreamReader(
                inputStream
            )
        )
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }

    companion object {
        const val FRAGMENT_TAG = "ScriptListFragment.TAG"
        private const val CODE = 200
        fun newInstance(): ScriptListFragment {
            return ScriptListFragment()
        }
    }
}
