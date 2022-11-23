package com.ooad.gomoku.engine

import com.ooad.gomoku.data.Move
import com.ooad.gomoku.data.Player
import com.ooad.gomoku.engine.state.*
import com.ooad.gomoku.observer.ResultPublisher
import com.ooad.gomoku.observer.StatSaver

enum class StateEnum {
    INIT,
    WHITE_TO_PLAY,
    BLACK_TO_PLAY,
    TERMINATED
}

class GameEngine(private val player: Player) : EngineInterface {
    private val publisher = ResultPublisher
    private val states = mapOf<StateEnum, State>(
        StateEnum.INIT to Init(),
        StateEnum.WHITE_TO_PLAY to WhiteToPlay(this),
        StateEnum.BLACK_TO_PLAY to BlackToPlay(this),
        StateEnum.TERMINATED to Terminated()
    )
    private var state: State

    init {
        publisher.registerObserver(StatSaver())
        state = states[StateEnum.INIT]!!
    }

    fun readyToPlay() {
        changeState(StateEnum.WHITE_TO_PLAY)
    }

    fun changeState(stateEnum: StateEnum) {
        state = states[stateEnum]!!
    }

    override fun move(move: Move) {
        TODO("Not yet implemented")
    }
}