package com.smim.projekt3.puzzle

import android.view.MotionEvent
import android.view.View
import com.smim.projekt3.puzzle.PuzzlePiece
import java.lang.Math.*

class TouchListener(
    private val endGameListener: EndGameListener
) : View.OnTouchListener{
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val piece = v as PuzzlePiece

        if(!piece.canMove)
            return true

        when {
            pieceIsInMotion(event!!) -> handleMotionEvent(event, piece)
            pieceIsGrabbed(event) -> handleGrabEvent(piece)
            pieceIsReleased(event) -> handleReleaseEvent(piece)
        }
        return true
    }

    private fun pieceIsInMotion(event: MotionEvent) : Boolean{
        return event!!.action == MotionEvent.ACTION_MOVE
    }
    private fun pieceIsGrabbed(event: MotionEvent) : Boolean{
        return event!!.action == MotionEvent.ACTION_DOWN
    }
    private fun pieceIsReleased(event: MotionEvent) : Boolean{
        return event!!.action == MotionEvent.ACTION_UP
    }

    private fun handleMotionEvent(event: MotionEvent, piece: PuzzlePiece){
        piece!!.y = event.rawY - piece.height
        piece.x = event.rawX - piece.width
    }

    private fun handleGrabEvent(piece: PuzzlePiece){
        piece.bringToFront()
        piece.xStartMove = piece.x
        piece.yStartMove = piece.y
    }

    private fun handleReleaseEvent(piece: PuzzlePiece){
        when{
            pieceIsNearCorrectPlace(piece) -> lockPieceInCorrectPlace(piece)
            pieceShouldBeReturnedToBottomBoard(piece) -> movePieceToBottomBoard(piece)
        }
        endGameListener.triggerEndGameEvent()
    }

    private fun pieceIsNearCorrectPlace(piece: PuzzlePiece) : Boolean {
        val tolerance = sqrt(pow(piece.width.toDouble(), 2.toDouble()) + pow(piece.height.toDouble(), 2.toDouble())) / 10
        val xDistance = abs(piece.xCoordinate - piece.x)
        val yDistance = abs(piece.yCoordinate - piece.y)

        return xDistance <= tolerance && yDistance <= tolerance
    }

    private fun pieceShouldBeReturnedToBottomBoard(piece: PuzzlePiece) : Boolean{
        return piece.y <= piece.bottomBorder
    }

    private fun lockPieceInCorrectPlace(piece: PuzzlePiece){
        piece.x = piece.xCoordinate.toFloat()
        piece.y = piece.yCoordinate.toFloat()
        piece.canMove = false
    }

    private fun movePieceToBottomBoard(piece: PuzzlePiece){
        piece.x = piece.xStartMove
        piece.y = piece.yStartMove
    }

}