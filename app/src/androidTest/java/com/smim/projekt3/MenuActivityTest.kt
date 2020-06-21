package com.smim.projekt3

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smim.projekt3.menu.MenuActivity
import com.smim.projekt3.puzzle.PuzzleActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MenuActivityTest {

    @Test
    fun testMenuActivity(){
        val scenario = ActivityScenario.launch(MenuActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
    }

}