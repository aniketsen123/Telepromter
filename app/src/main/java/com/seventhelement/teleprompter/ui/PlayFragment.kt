package com.seventhelement.teleprompter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.seventhelement.teleprompter.R
import com.seventhelement.teleprompter.persistance.entities.Script

class PlayFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "PlayFragment.TAG"

        @JvmStatic
        fun newInstance(script: Script): PlayFragment {
            val fragment = PlayFragment()
            val args = Bundle().apply {
                putParcelable(ARG_PARAM1, script)
            }
            fragment.arguments = args
            return fragment
        }

        private const val ARG_PARAM1 = "param1"
    }

    private lateinit var scriptView: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var controlButton: ImageButton
    private lateinit var seekBar: SeekBar
    private lateinit var progressTextView: TextView
    private lateinit var controlsView: CardView
    private lateinit var view: View

    private var script: Script? = null
    private var activity: MainActivity? = null
    private var scrollTo = 100
    private var isPlaying = false
    private var hasReachedBottom = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            script = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_play, container, false)

        scriptView = view.findViewById(R.id.scriptTextView)
        scrollView = view.findViewById(R.id.scriptScrollView)
        controlButton = view.findViewById(R.id.controlButton)
        seekBar = view.findViewById(R.id.speedSeekBar)
        progressTextView = view.findViewById(R.id.speedValue)
        controlsView = view.findViewById(R.id.controls)

        initViews()

        controlButton.setOnClickListener {
            if (isPlaying) {
                controlButton.setImageResource(R.drawable.ic_pause)
                // pause()
            } else {
                controlButton.setImageResource(R.drawable.ic_play)
                scroll()
            }
            isPlaying = !isPlaying

            if (hasReachedBottom) {
                showDoneScrollingMessage()
            }
        }

        scriptView.setOnClickListener {
            isPlaying = !isPlaying
            showControls()
        }

        return view
    }

    private fun initViews() {
        activity = getActivity() as MainActivity
        activity?.supportActionBar?.hide()

        scriptView.text = script?.body
        scriptView.textSize = 45f

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) updateSpeed(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            hasReachedBottom = scrollView.getChildAt(0).bottom <= (scrollView.height + scrollView.scrollY)

            if (!hasReachedBottom && isPlaying) {
                scroll()
            }


            if (scrollView.getChildAt(0).bottom == (scrollView.height + scrollView.scrollY)) {
                showDoneScrollingMessage()
            }
        }
    }

    private fun showDoneScrollingMessage() {
        Toast.makeText(context, "Script done", Toast.LENGTH_LONG).show()
    }

    private fun updateSpeed(progress: Int) {
        progressTextView.text = progress.toString()
        // scriptBodyEt.lineCount
    }

    fun scroll() {
        hideControls()
        scrollView.postDelayed({
            scrollView.smoothScrollTo(0, scrollTo)
        }, 100)
        scrollTo += 10
    }

    private fun hideControls() {
        scrollView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        controlsView.visibility = View.INVISIBLE
    }

    private fun showControls() {
        controlsView.visibility = View.VISIBLE
    }
}