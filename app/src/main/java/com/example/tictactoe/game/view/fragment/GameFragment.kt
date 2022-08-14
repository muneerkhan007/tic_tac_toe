package com.example.tictactoe.game.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.*
import androidx.fragment.app.viewModels
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.databinding.LayoutTicTacToeBoardCellBinding
import com.example.tictactoe.databinding.LayoutTicTacToeBoardRowBinding
import com.example.tictactoe.game.model.GameResultModel
import com.example.tictactoe.game.model.GameStateModel
import com.example.tictactoe.game.viewmodel.GameViewModel

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val viewModel by viewModels<GameViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.observableState.observe(viewLifecycleOwner, ::onStateChanged)
        setRestartGameButtonOnClickListener()
    }

    private fun setRestartGameButtonOnClickListener() {
        binding.buttonTicTacToeGameRestartGame.setOnClickListener {
            viewModel.restartGame()
        }
    }

    private fun onStateChanged(state: GameStateModel) {
        showPlayerTurn(state.playerTurn)
        showBoard(state.board)
        showGameResult(state.result)
    }

    private fun showPlayerTurn(playerTurn: String) {
        with(binding.textViewTicTacToeGamePlayerTurn) {
            text = getString(R.string.ticTacToeGame_playerTurn, playerTurn)
        }
    }

    private fun showBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            if (isEmpty()) {
                drawBoard(board)
            } else {
                updateBoard(board)
            }
        }
    }

    private fun drawBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            board.forEachIndexed { i, row ->
                val rowBinding = LayoutTicTacToeBoardRowBinding.inflate(
                    layoutInflater,
                    this,
                    true
                )
                row.forEachIndexed { j, cell ->
                    val cellBinding = LayoutTicTacToeBoardCellBinding.inflate(
                        layoutInflater,
                        rowBinding.root,
                        true
                    )
                    cellBinding.root.text = cell
                    cellBinding.root.setOnClickListener {
                        viewModel.makeMove(i to j)
                    }
                }
            }
        }
    }

    private fun updateBoard(board: List<List<String>>) {
        with(binding.viewTicTacToeGameBoard.tableLayoutTicTacToeGameBoard) {
            forEachIndexed { i, rowView ->
                (rowView as ViewGroup).forEachIndexed { j, cellView ->
                    (cellView as TextView).text = board[i][j]
                }
            }
        }
    }

    private fun showGameResult(result: GameResultModel?) {
        with(binding.viewTicTacToeGameResult) {
            root.isInvisible = result == null
            if (result == null) return
            textViewTicTacToeGameResult.setText(result.nameId)
            val winnerId = result.winnerId
            linearLayoutTicTacToeGameResultPlayers.forEach {
                it.isVisible = winnerId == null || winnerId == it.id
            }
        }
    }
}