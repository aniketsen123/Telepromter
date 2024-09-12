package com.seventhelement.teleprompter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script

class PlayScriptFragment : Fragment() {

    private var listener: PlayScriptListener? = null
    private lateinit var scriptView: TextView
    private lateinit var scrollView: ScrollView

    private var script: Script? = null
    private var activity: MainActivity? = null

    interface PlayScriptListener {
        fun onPlayScript(script: Script)
    }

    companion object {
        const val FRAGMENT_TAG = "PlayScriptFragment.TAG"
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(script: Script): PlayScriptFragment {
            return PlayScriptFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, script)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            script = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play_script, container, false)
        scriptView = view.findViewById(R.id.scriptTextView)
        scrollView = view.findViewById(R.id.scriptScrollView)

        activity = getActivity() as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        return view
    }

    fun setListener(listener: PlayScriptListener) {
        this.listener = listener
    }

    private fun initViews() {
        script?.let {
            activity?.supportActionBar?.title = it.title
            scriptView.text = it.body
        }
    }

    fun scroll() {
        scrollView.postDelayed({
            scrollView.smoothScrollTo(0, scriptView.bottom / 2)
        }, 1000)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_preview, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_play -> {
                script?.let { listener?.onPlayScript(it) }
                true
            }
            android.R.id.home -> {
                activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                activity?.supportFragmentManager?.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
