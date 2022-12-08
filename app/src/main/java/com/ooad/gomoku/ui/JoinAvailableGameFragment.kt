package com.ooad.gomoku.ui

import android.app.Activity
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
import com.skyfishjy.library.RippleBackground

//Fragment residing in JoinActivity to show available games
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

        val listview: ListView? = getView()?.findViewById(R.id.games_list)
        gamesListAdapter = GamesListAdapter(requireActivity(), games)
        listview?.adapter = gamesListAdapter
        listview?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                joinGame(adapterView.getItemAtPosition(position) as String)
            }
        val rippleBackground = getView()?.findViewById<View>(R.id.ripple) as RippleBackground
        rippleBackground.startRippleAnimation()
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity().intent.getBooleanExtra(KEY_GAME_STARTED, false)) {
            requireActivity().finish()
            return
        }
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
                view?.findViewById<TextView>(R.id.available_label)?.visibility = View.VISIBLE
            }
        }

    }

    private fun addGame(newGame: String) {
        if (!games.contains(newGame)) {
            games.add(newGame)
            displayGames()
        }
    }

    private fun joinGame(gameName: String) {
        viewModel.connectToServer(gameName) {
            startActivity(Intent(requireActivity(), GameActivity::class.java).apply {
                putExtra(KEY_PLAYER_NAME, playerName)
                putExtra(KEY_PLAYER_TYPE, "Peer")
            })
            requireActivity().intent.putExtra(KEY_GAME_STARTED, true)
        }
    }
}

class GamesListAdapter(private val ctx: Activity, private val games: MutableList<String>) :
    ArrayAdapter<String>(ctx, R.layout.material_list_item_single_line, games) {

    class ViewHolder {
        lateinit var image: ImageView
        lateinit var name: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val conView: View
        return if (convertView == null) {
            viewHolder = ViewHolder()
            conView = ctx.layoutInflater.inflate(R.layout.material_list_item_single_line, parent, false)
            viewHolder.image = conView.findViewById(R.id.material_list_item_icon)
            viewHolder.image.setImageResource(R.drawable.logo)
            viewHolder.name = conView.findViewById(R.id.material_list_item_text)
            viewHolder.name.text = games[position]
            conView.tag = viewHolder
            conView
        } else {
            viewHolder = convertView.tag as ViewHolder
            viewHolder.name.text = games[position]
            convertView
        }
    }
}

const val KEY_PLAYER_NAME = "com.ooad.gomoku.PLAYER_NAME"