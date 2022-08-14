package com.example.tictactoe.game.model

sealed class GameStatusModel {
    object InProgress : GameStatusModel()
    object Draw : GameStatusModel()
    data class Win(val winner: PlayerModel) : GameStatusModel()
}