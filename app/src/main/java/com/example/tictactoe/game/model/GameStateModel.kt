package com.example.tictactoe.game.model

import com.example.tictactoe.common.BaseViewState

data class GameStateModel(
    val playerTurn: String = "",
    val board: List<List<String>> = emptyList(),
    val result: GameResultModel? = null
) : BaseViewState