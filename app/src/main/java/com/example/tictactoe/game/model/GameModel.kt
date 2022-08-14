package com.example.tictactoe.game.model

class GameModel(
    val board: List<List<PlayerModel?>>,
    val currentPlayer: PlayerModel,
    val status: GameStatusModel
) {

}