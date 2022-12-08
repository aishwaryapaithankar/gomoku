package com.ooad.gomoku.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ooad.gomoku.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            findViewById<Button>(R.id.host_game).setOnClickListener(hostGame)
            findViewById<Button>(R.id.join_button).setOnClickListener(joinGame)
        }
    }

    private val hostGame: (View) -> Unit = {
        startActivity(Intent(requireActivity(), HostActivity::class.java))
    }

    private val joinGame: (View) -> Unit = {
        startActivity(Intent(requireActivity(), JoinActivity::class.java))
    }
}