package com.seventhelement.teleprompter.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script
import com.seventhelement.teleprompter.viewmodel.ScriptViewModel

class AddScriptFragment : Fragment() {

   companion object{
        const val FRAGMENT_TAG = "AddScriptFragment.TAG"

        @JvmStatic
        fun newInstance(scriptBody: String?): AddScriptFragment {
            val fragment = AddScriptFragment()
            fragment.scriptBody = scriptBody
            return fragment
        }
    }

    private var scriptBody: String? = null


     lateinit var scriptBodyEt: EditText


    lateinit var scriptTitleEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        scriptBodyEt=view.findViewById(R.id.scriptText)
        scriptTitleEt=view.findViewById(R.id.scriptTitle)
        val activity = activity as MainActivity
        val bar: ActionBar? = activity.supportActionBar

        bar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.create_new_title)
        }

        scriptBody?.let {
            scriptBodyEt.setText(it)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                saveScript()
                true
            }
            android.R.id.home -> {
                goHome()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveScript() {
        val viewModel = ViewModelProvider(this).get(ScriptViewModel::class.java)

        var title = scriptTitleEt.text.toString()
        val body = scriptBodyEt.text.toString()

        if (body.isNotEmpty()) {
            title = if (title.isNotEmpty()) title else "Untitled"

            val script = Script().apply {
                this.title = title
                this.body = body
                this.dateInMilli = System.currentTimeMillis()
            }
            viewModel.saveNewScript(script)

            goHome()
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goHome() {
        hideKeyboard(activity)
        activity?.supportFragmentManager?.popBackStack()
    }


        fun hideKeyboard(activity: Activity?) {
            val view = activity?.findViewById<View>(android.R.id.content)
            view?.let {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

    }
}