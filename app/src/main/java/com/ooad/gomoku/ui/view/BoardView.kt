package com.ooad.gomoku.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.ooad.gomoku.R
import kotlin.math.min

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var boardSize: Int
    private var boardMeasure: Float
    private var boardLen: Int = 0
    private var boardLinePaint: Paint

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0).apply {
            try {
                boardSize = getInteger(R.styleable.BoardView_size, 0)
                boardMeasure = getDimension(R.styleable.BoardView_measure, 0f)
            } finally {
                recycle()
            }
        }

        boardLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLines(canvas)
    }

    private fun drawLines(canvas: Canvas) {
        var x = 0f
        var y = 0f
        for (i in 0..boardSize) {
            // vertical lines
            canvas.drawLine(x, 0f, x, boardLen.toFloat(), boardLinePaint)
            x += boardLen.toFloat() / boardSize
            // horizontal lines
            canvas.drawLine(0f, y, boardLen.toFloat(), y, boardLinePaint)
            y  += boardLen.toFloat() / boardSize
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "onSizeChanged: w=$w, h=$h")
        boardLen = min(w, h)
    }
}

private const val TAG = "BoardView"