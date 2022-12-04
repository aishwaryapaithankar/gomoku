package com.ooad.gomoku.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ooad.gomoku.R

class PlayerInfoComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var colorImageView: ImageView
    var imageResource: Int = 0
        set(value) {
            field = value
            colorImageView.setImageResource(imageResource)
        }

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
            wonTextView.text = "Won: $value"
        }

    private lateinit var lostTextView: TextView
    var gamesLost: Int = 0
        set(value) {
            field = value
            lostTextView.text = "Lost: $value"
        }

    private lateinit var drawnTextView: TextView
    var gamesDrawn: Int = 0
        set(value) {
            field = value
            drawnTextView.text = "Drawn: $value"
        }

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.component_player_info, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        colorImageView = findViewById(R.id.color_image)
        nameTextView = findViewById(R.id.player_name_tv)
        wonTextView = findViewById(R.id.games_won_tv)
        lostTextView = findViewById(R.id.games_lost_tv)
        drawnTextView = findViewById(R.id.games_drawn_tv)
    }
}