package com.smim.projekt3.puzzle


import android.content.Context
import androidx.appcompat.widget.AppCompatImageView

class PuzzlePiece(
    context: Context,
    val xCoordinate: Int,
    val yCoordinate: Int,
    val pieceWidth: Int,
    val pieceHeight: Int,
    val bottomBorder: Int,
    var canMove : Boolean = true,
    var xStartMove : Float = 0F,
    var yStartMove : Float = 0F
) : AppCompatImageView(context)