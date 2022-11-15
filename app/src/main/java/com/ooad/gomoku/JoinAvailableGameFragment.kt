package com.ooad.gomoku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class JoinAvailableGameFragment : Fragment() {

    companion object {
        fun newInstance() = JoinAvailableGameFragment()
    }

    private lateinit var viewModel: JoinAvailableGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_available_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JoinAvailableGameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}