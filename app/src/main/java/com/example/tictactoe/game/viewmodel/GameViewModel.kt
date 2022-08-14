package com.example.tictactoe.game.viewmodel

import com.example.tictactoe.R
import com.example.tictactoe.common.BaseViewModel
import com.example.tictactoe.game.model.*
import com.example.tictactoe.utils.GameUtils

class GameViewModel: BaseViewModel<GameStateModel>() {
    override val initialState = GameStateModel()
    private val gameEngine = GameUtils()

    init {
        state = initialState
        getInitialGameInfo()
    }

    private fun getInitialGameInfo(){
        val game = gameEngine.getGameInfo()
        setGameState(game)
    }

    fun makeMove(position: Pair<Int, Int>) {
        try {
            val game = gameEngine.makeMove(position)
            setGameState(game)
        } catch (e: GameUtils.GameIsOverException) {
        } catch (e: GameUtils.InvalidPositionException) {
        }
    }

    fun restartGame() {
        val game  = gameEngine.restartGame()
        setGameState(game)
    }

    private fun setGameState(game: GameModel) {
        state = state.copy(
            playerTurn = game.currentPlayer.name,
            board = game.board.map { row -> row.map { it?.name ?: "" } },
            result = game.status.toResultModel()
        )
    }

    fun GameStatusModel.toResultModel(): GameResultModel? {
        return when (this) {
            GameStatusModel.InProgress -> null
            GameStatusModel.Draw -> {
                GameResultModel(
                    nameId = R.string.ticTacToeGameResult_draw,
                    winnerId = null
                )
            }
            is GameStatusModel.Win -> {
                val playerViewId = when (winner) {
                    PlayerModel.X -> R.id.textView_ticTacToeGameResult_playerX
                    PlayerModel.O -> R.id.textView_ticTacToeGameResult_playerO
                }
                GameResultModel(
                    nameId = R.string.ticTacToeGameResult_winner,
                    winnerId = playerViewId
                )
            }
        }
    }
}