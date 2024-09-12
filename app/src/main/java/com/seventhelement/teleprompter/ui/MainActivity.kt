package com.seventhelement.teleprompter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script

class MainActivity : AppCompatActivity(),
    ScriptAdapter.OnScriptSelectedListener,
    PlayScriptFragment.PlayScriptListener,
    ScriptListFragment.OnAddScriptListener {

    private lateinit var bar: Toolbar
    private val SCRIPT_KEY = "Script.key"
    private var script: Script? = null
    private var isPreviewMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar = findViewById(R.id.toolbar)
        setSupportActionBar(bar)

        // Initialize the first fragment
        if (savedInstanceState == null) {
            val fragment = ScriptListFragment.newInstance()
            fragment.setListener(this)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(ScriptListFragment.FRAGMENT_TAG)
                .commit()
        }
    }

    override fun scriptSelected(script: Script) {

        Log.d("FragmentTransition", "scriptSelected called")
        this.script = script
        val fragment = PlayScriptFragment.newInstance(script)
        fragment.setListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(PlayScriptFragment.FRAGMENT_TAG)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SCRIPT_KEY, script)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        // Custom behavior for back press
        super.onBackPressed()
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onPlayScript(script: Script) {
        Log.d("FragmentTransition", "onPlayScript called")
        isPreviewMode = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlayFragment.newInstance(script))
            .addToBackStack(PlayFragment.FRAGMENT_TAG)
            .commit()
    }

    override fun createNewScript(scriptBody: String?) {
        Log.d("FragmentTransition", "createNewScript called")
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddScriptFragment.newInstance(scriptBody))
            .addToBackStack(AddScriptFragment.FRAGMENT_TAG)
            .commit()
    }
}
