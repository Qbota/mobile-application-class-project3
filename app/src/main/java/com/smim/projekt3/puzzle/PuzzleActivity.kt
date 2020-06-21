package com.smim.projekt3.puzzle

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.drawToBitmap
import com.smim.projekt3.R
import com.smim.projekt3.menu.MenuActivity
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class PuzzleActivity : AppCompatActivity(), EndGameListener {
    private val ROWS_COUNT = 4
    private val COLS_COUNT = 3
    private val pieces = ArrayList<PuzzlePiece>()
    private var startTime : Long = 0
    private var endTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<RelativeLayout>(R.id.layout)
        val image = findViewById<ImageView>(R.id.imageView)
        val resId = resources.getIdentifier(intent.getStringExtra("IMAGE"), "drawable", packageName)
        image.setImageResource(resId)

        createPuzzlePieces(layout, image, pieces)
        startTime = Date().time
    }

    private fun createPuzzlePieces(
        layout: RelativeLayout,
        image: ImageView,
        pieces: ArrayList<PuzzlePiece>
    ) {
        layout.post {
            splitImage(image, pieces)
            for(piece in pieces){
                setUpPuzzlePieceOnBoard(piece, image, layout)
            }
        }
    }

    private fun splitImage(image : ImageView, pieces: ArrayList<PuzzlePiece>){
        val bitmap = image.drawToBitmap()
        val pieceWidth = bitmap.width / COLS_COUNT
        val pieceHeight = bitmap.height / ROWS_COUNT

        var yCoordinate = 0
        for(row in 0 until ROWS_COUNT){
            var xCoordinate = 0
            for(col in 0 until COLS_COUNT){
                val piece = PuzzlePiece(
                    applicationContext,
                    xCoordinate + image.left,
                    yCoordinate + image.top,
                    pieceWidth,
                    pieceHeight,
                    image.bottom
                )
                val pieceBitmap = Bitmap.createBitmap(bitmap, xCoordinate, yCoordinate, pieceWidth, pieceHeight)
                piece.setImageBitmap(pieceBitmap)
                pieces.add(piece)
                xCoordinate += pieceWidth
            }
            yCoordinate += pieceHeight
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpPuzzlePieceOnBoard(piece : PuzzlePiece, image: ImageView, layout: RelativeLayout){
        piece.setOnTouchListener(TouchListener(this))
        piece.x = Random.nextInt(layout.left, layout.right - piece.pieceWidth).toFloat()
        piece.y = Random.nextInt(image.bottom, layout.bottom - piece.pieceHeight).toFloat()
        layout.addView(piece)
    }

    override fun triggerEndGameEvent() {
        if(pieces.all {!it.canMove}){
            endTime = Date().time
            val playTime = ((endTime - startTime) / 1000).toInt()
            val alertDialog = createAlertDialog(playTime)
            alertDialog.show()
        }
    }


    private fun createAlertDialog(playTime : Int) : AlertDialog{
        return AlertDialog.Builder(this@PuzzleActivity)
        .setTitle("Congratulations!")
        .setMessage(String.format("You have finished your puzzle in %s s. That't a great time!", playTime))
        .setPositiveButton("Ok"){
                _,_ ->
            goBackToMenu()
        }
        .create()
    }

    private fun goBackToMenu(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }


}