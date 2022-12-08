package com.ooad.gomoku.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.ooad.gomoku.R

class JoinGameFragment : Fragment() {

    companion object {
        fun newInstance() = JoinGameFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getView()?.findViewById<Button>(R.id.join_button)?.setOnClickListener {
            val playerName =
                getView()?.findViewById<TextInputLayout>(R.id.input_name)?.editText?.text.toString()
            if (playerName.isEmpty())
                return@setOnClickListener
            val availableGameFragment =
                JoinAvailableGameFragment.newInstance(bundleOf(KEY_PLAYER_NAME to playerName))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.join_activity_container, availableGameFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}