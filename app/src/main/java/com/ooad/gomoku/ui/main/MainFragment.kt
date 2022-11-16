package com.ooad.gomoku.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.HostActivity
import com.ooad.gomoku.JoinActivity
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
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val hostButton: Button = view.findViewById(R.id.host_game)
        hostButton.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, HostActivity::class.java))
            }
        }
        val joinButton: Button = view.findViewById(R.id.join_button)
        joinButton.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, JoinActivity::class.java))
            }
        }
        return view
    }

}