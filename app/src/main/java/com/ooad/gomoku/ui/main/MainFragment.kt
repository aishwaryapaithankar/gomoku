package com.ooad.gomoku.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ooad.gomoku.R
import android.content.Intent
import com.ooad.gomoku.HostActivity
import com.ooad.gomoku.JoinActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false);
        val hostButton : Button = view.findViewById<Button>(R.id.host_game);
        hostButton.setOnClickListener {
            requireActivity().run{
                startActivity(Intent(this, HostActivity::class.java))
            }
        }
        val joinButton : Button = view.findViewById<Button>(R.id.join_button);
        joinButton.setOnClickListener {
            requireActivity().run{
                startActivity(Intent(this, JoinActivity::class.java))
            }
        }
        return view;
    }

}