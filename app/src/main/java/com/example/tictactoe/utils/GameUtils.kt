package com.example.tictactoe.utils

import com.example.tictactoe.game.model.GameModel
import com.example.tictactoe.game.model.GameStateModel
import com.example.tictactoe.game.model.GameStatusModel
import com.example.tictactoe.game.model.PlayerModel
import java.util.*
import kotlin.collections.ArrayList

class GameUtils {
    companion object {
        const val BOARD_SIZE = 3
    }
    private var board = Array(BOARD_SIZE) { Array<PlayerModel?>(BOARD_SIZE) { null } }
    private var gameStatus: GameStatusModel = GameStatusModel.InProgress
    private var moveCount = 0
    private var firstPlayer = PlayerModel.X
    private var currentPlayer = firstPlayer

    fun getGameInfo(): GameModel {
        return GameModel(
            board = board.map { rowArray -> rowArray.toList() },
            currentPlayer = currentPlayer,
            status = gameStatus
        )
    }

    fun restartGame(): GameModel {
        board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null } }
        if (gameStatus != GameStatusModel.InProgress) {
            firstPlayer = togglePlayer(firstPlayer)
        }
        currentPlayer = firstPlayer
        gameStatus = GameStatusModel.InProgress
        moveCount = 0
        return getGameInfo()
    }

    fun makeMove(pos: Pair<Int, Int>): GameModel {
        if(gameStatus != GameStatusModel.InProgress) {
            throw GameIsOverException()
        }
        if(board[pos.first][pos.second] != null) {
            throw InvalidPositionException()
        }
        board[pos.first][pos.second] = currentPlayer
        moveCount++
        var row = 0
        var col = 0
        var diagonal = 0
        var r_diagonal = 0
        for (i in board.indices) {
            if (board[pos.first][i] == currentPlayer) row++
            if (board[i][pos.second] == currentPlayer) col++
            if (board[i][i] == currentPlayer) diagonal++
            if (board[i][board.lastIndex - i] == currentPlayer) r_diagonal++
        }
        val boardSize = board.size

        gameStatus = when {
            row == boardSize || col == boardSize || diagonal == boardSize || r_diagonal == boardSize -> {
                GameStatusModel.Win(winner = currentPlayer)
            }
            moveCount == boardSize * boardSize -> {
                GameStatusModel.Draw
            }
            else -> {
                GameStatusModel.InProgress
            }
        }
        currentPlayer = togglePlayer(currentPlayer)
        return getGameInfo()
    }

    private fun togglePlayer(player: PlayerModel): PlayerModel {
        return when (player) {
            PlayerModel.X -> PlayerModel.O
            PlayerModel.O -> PlayerModel.X
        }
    }

    class GameIsOverException : Exception()
    class InvalidPositionException : Exception()
}