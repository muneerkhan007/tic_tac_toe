package com.example.tictactoe.game.model

import androidx.annotation.IdRes
import androidx.annotation.StringRes

data class GameResultModel(
    @StringRes val nameId: Int,
    @IdRes val winnerId: Int?
)