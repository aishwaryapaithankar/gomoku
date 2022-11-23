package com.ooad.gomoku.engine.state

import com.ooad.gomoku.data.Move

interface State {
    fun move(move: Move) : Boolean
}