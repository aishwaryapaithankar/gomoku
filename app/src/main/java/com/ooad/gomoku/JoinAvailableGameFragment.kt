package com.ooad.gomoku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connManager = ConnectionManager(requireActivity())
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
        Toast.makeText(requireActivity(), "Player Name $playerName", Toast.LENGTH_SHORT).show()

        val listview : ListView? = getView()?.findViewById(R.id.games_list)

        val games = arrayListOf("A","B","C")
        val listAdapter : ArrayAdapter<*> = ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_1, games)
        listview?.adapter = listAdapter

        games.add("D")
        listAdapter.notifyDataSetChanged()
        listview?.onItemClickListener = AdapterView.OnItemClickListener {adapterView, _, position, _ ->
            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(requireActivity(),"click item $selectedItem its position $itemIdAtPos",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        connManager.initClient()
    }

    override fun onPause() {
        connManager.tearDownService()
        super.onPause()
    }

    override fun onDestroy() {
        connManager.tearDown()
        super.onDestroy()
    }
}

const val KEY_PLAYER_NAME = "com.ooad.gomoku.PLAYER_NAME"