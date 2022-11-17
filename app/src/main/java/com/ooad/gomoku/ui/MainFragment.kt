package com.ooad.gomoku.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            findViewById<Button>(R.id.host_game).setOnClickListener(::hostGame)
            findViewById<Button>(R.id.join_button).setOnClickListener(::joinGame)
        }
    }

    private fun hostGame(v: View) = requireActivity().run {
        startActivity(Intent(this, HostActivity::class.java))
    }

    private fun joinGame(v: View) = requireActivity().run {
        startActivity(Intent(this, JoinActivity::class.java))
    }
}