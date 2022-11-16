package com.ooad.gomoku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf

class JoinGameFragment : Fragment() {

    companion object {
        fun newInstance() = JoinGameFragment()
    }

    private lateinit var viewModel: JoinGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[JoinGameViewModel::class.java]

        getView()?.findViewById<Button>(R.id.join_button)?.setOnClickListener {
            val playerName = getView()?.findViewById<EditText>(R.id.input_name)?.text.toString()
            val availableGameFragment =
                JoinAvailableGameFragment.newInstance(bundleOf(KEY_PLAYER_NAME to playerName))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.join_activity_container, availableGameFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}