package com.ooad.gomoku.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ooad.gomoku.GomokuApp
import com.ooad.gomoku.R
import com.ooad.gomoku.network.ConnectionManager

class JoinAvailableGameFragment : Fragment() {

    companion object {
        fun newInstance(args: Bundle? = null) = JoinAvailableGameFragment().apply {
            arguments = args
        }
    }

    private lateinit var viewModel: JoinAvailableGameViewModel
    private lateinit var connManager: ConnectionManager
    private lateinit var playerName: String
    private var games: MutableList<String> = arrayListOf()
    private lateinit var gamesListAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connManager = ConnectionManager(requireActivity()).apply {
            serverDiscoveredCallback = ::addGame
            val app = this@JoinAvailableGameFragment.requireActivity().application as GomokuApp
            app.connManager = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playerName = arguments?.getString(KEY_PLAYER_NAME) ?: ""
        return inflater.inflate(R.layout.fragment_join_available_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[JoinAvailableGameViewModel::class.java]
        viewModel.connManager = connManager

        Toast.makeText(requireActivity(), "Player Name $playerName", Toast.LENGTH_SHORT).show()

        val listview: ListView? = getView()?.findViewById(R.id.games_list)
        gamesListAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, games)
        listview?.adapter = gamesListAdapter
        listview?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                joinGame(adapterView.getItemAtPosition(position) as String)
                Toast.makeText(
                    requireActivity(),
                    "click item at position $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onResume() {
        super.onResume()
        searchGame()
    }

    override fun onPause() {
        connManager.tearDownService()
        games.clear()
        super.onPause()
    }

    override fun onDestroy() {
        connManager.tearDown()
        super.onDestroy()
    }

    private fun searchGame() {
        connManager.initClient()
    }

    private fun displayGames() {
        requireActivity().runOnUiThread {
            gamesListAdapter.notifyDataSetChanged()
            if (games.size > 0) {
                val label: TextView? = view?.findViewById(R.id.available_label)
                label?.visibility = View.VISIBLE
            }
        }

    }

    private fun addGame(newGame: String) {
        games.add(newGame)
        displayGames()
    }

    private fun joinGame(gameName: String) {
        viewModel.connectToServer(gameName) {
            startActivity(Intent(requireActivity(), GameActivity::class.java).apply {
                putExtra(KEY_PLAYER_NAME, playerName)
                putExtra(KEY_PLAYER_TYPE, "Peer")
            })
        }
    }
}

const val KEY_PLAYER_NAME = "com.ooad.gomoku.PLAYER_NAME"