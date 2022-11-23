package com.ooad.gomoku.engine.state

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.engine.GameEngine
import com.ooad.gomoku.engine.StateEnum

class BlackToPlay(private val engine: GameEngine) : State {
    override fun move(move: Move) {
        engine.changeState(StateEnum.WHITE_TO_PLAY)
    }
}