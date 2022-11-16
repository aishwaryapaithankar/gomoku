package com.ooad.gomoku

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class JoinAvailableGameFragment : Fragment() {

    companion object {
        fun newInstance() = JoinAvailableGameFragment()
    }

    private lateinit var viewModel: JoinAvailableGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_join_available_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[JoinAvailableGameViewModel::class.java]
        val listview : ListView? = getView()?.findViewById(R.id.games_list)

        val games = arrayListOf<String>("A","B","C")
        val listAdapter : ArrayAdapter<*> = ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_1, games)
        listview?.adapter = listAdapter

        games.add("D")
        listAdapter.notifyDataSetChanged()
        listview?.onItemClickListener = AdapterView.OnItemClickListener {adapterView, view, position, id ->
            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(requireActivity(),"click item $selectedItem its position $itemIdAtPos",Toast.LENGTH_SHORT).show()
        }
    }

}