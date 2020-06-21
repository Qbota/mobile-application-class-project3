package com.smim.projekt3.menu

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import com.smim.projekt3.R
import com.smim.projekt3.puzzle.PuzzleActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun startGame(view : View){
        val image = view as ImageView
        val intent = Intent(this, PuzzleActivity::class.java)
        val tag = image.tag as String
        intent.putExtra("IMAGE", tag)
        startActivity(intent)
    }

}