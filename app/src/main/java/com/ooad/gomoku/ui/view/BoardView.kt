package com.ooad.gomoku.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.ooad.gomoku.R
import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.Piece
import kotlin.math.floor
import kotlin.math.min

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var boardSize: Int
        private set
    private var boardMeasure: Float
    private var boardLen: Int = 0

    private var blackPaint: Paint
    private var whitePaint: Paint
    private var blackBorderPaint: Paint

    private var logicalBoard: Array<Array<Piece>>

    lateinit var engineMoveCallback: (Move) -> Unit
    lateinit var piece: Piece

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0).apply {
            try {
                boardSize = getInteger(R.styleable.BoardView_size, 0)
                boardMeasure = getDimension(R.styleable.BoardView_measure, 0f)
            } finally {
                recycle()
            }
        }

        logicalBoard = Array(boardSize) { Array(boardSize) { Piece.NONE } }

        blackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 3f
        }
        whitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
        blackBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLines(canvas)
        drawPieces(canvas)
    }

    private fun drawLines(canvas: Canvas) {
        var x = 0f
        var y = 0f
        for (i in 0..boardSize) {
            // vertical lines
            canvas.drawLine(x, 0f, x, boardLen.toFloat(), blackPaint)
            x += boardLen.toFloat() / boardSize
            // horizontal lines
            canvas.drawLine(0f, y, boardLen.toFloat(), y, blackPaint)
            y  += boardLen.toFloat() / boardSize
        }
    }

    private fun drawPieces(canvas: Canvas) {
        val cOffset = boardLen.toFloat() / (boardSize * 2)
        val cRadius = cOffset * 0.8f

        for (row in logicalBoard.indices) {
            for (col in logicalBoard[row].indices) {
                val piece = logicalBoard[row][col]
                if (piece != Piece.NONE) {
                    val cx = (row.toFloat() * boardLen / boardSize) + cOffset
                    val cy = (col.toFloat() * boardLen / boardSize) + cOffset
                    val paint = if (piece == Piece.WHITE) whitePaint else blackPaint
                    canvas.drawCircle(cx, cy, cRadius, paint)
                    canvas.drawCircle(cx, cy, cRadius, blackBorderPaint)
                }
            }
        }
    }

    fun addPiece(x: Int, y: Int, piece: Piece) {
        if (logicalBoard[x][y] != Piece.NONE) return
        logicalBoard[x][y] = piece
        invalidate()
        requestLayout()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "onSizeChanged: w=$w, h=$h")
        boardLen = min(w, h)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun getBoardLocation(x: Float, y: Float) : Pair<Int, Int> {
        val xIndex = floor(x / boardLen * boardSize)
        val yIndex = floor(y / boardLen * boardSize)
        Log.i(TAG, "xIndex=$xIndex, yIndex=$yIndex")
        return xIndex.toInt() to yIndex.toInt()
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.i(TAG, "x=${e.x}, y=${e.y}")
            val (x, y) = getBoardLocation(e.x, e.y)
            if (x < 0 || x >= boardSize || y < 0 || y >= boardSize)
                return true

            engineMoveCallback(Move(x, y, piece))
            return true
        }
    }

    private val gestureDetector = GestureDetector(context, gestureListener)
}

private const val TAG = "BoardView"