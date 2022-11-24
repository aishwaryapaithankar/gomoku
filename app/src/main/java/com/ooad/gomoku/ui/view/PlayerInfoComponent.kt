package com.ooad.gomoku.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.ooad.gomoku.R

class PlayerInfoComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var nameTextView: TextView
    var playerName: String = String()
        set(value) {
            field = value
            nameTextView.text = value
        }

    private lateinit var wonTextView: TextView
    var gamesWon: Int = 0
        set(value) {
            field = value
            wonTextView.text = value.toString()
        }

    private lateinit var lostTextView: TextView
    var gamesLost: Int = 0
        set(value) {
            field = value
            lostTextView.text = value.toString()
        }

    private lateinit var drawnTextView: TextView

    private lateinit var totalTextView: TextView


    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.component_player_info, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        nameTextView = findViewById<TextView>(R.id.player_name_tv)
        wonTextView = findViewById<TextView>(R.id.games_won_tv)
        lostTextView = findViewById<TextView>(R.id.games_lost_tv)
    }
}