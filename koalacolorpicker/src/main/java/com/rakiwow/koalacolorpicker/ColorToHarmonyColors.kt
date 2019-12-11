package com.rakiwow.koalacolorpicker

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.max
import kotlin.math.min

class ColorToHarmonyColors {

    companion object {
        var colorArray: IntArray? = null
    }

    val c2 = 45
    val c3 = 50
    val c3_d = 10
    val c4 = 15
    val c4_d = 60
    val c5 = 120
    val c5_d = 130

    //If the user wants to get a complementary harmony color-scheme without using dark or light mode.
    fun complementary(color: Int) : IntArray{
        val colors = IntArray(5)
        colors[0] = color
        colors[1] = Color.rgb(min(color.red + c2, 255), min(color.green + c2, 255), min(color.blue + c2, 255))
        colors[2] = Color.rgb(max(color.red - c3, 0), max(color.green - c3, 0), max(color.blue - c3, 0))
        val comp = getComplementary(color)
        colors[3] = Color.rgb(min(comp.red + c4, 255), min(comp.green + c4, 255), min(comp. blue + c4, 255))
        colors[4] = Color.rgb(max(comp.red - c5, 0), max(comp.green - c5, 0), max(comp.blue - c5, 0))

        colorArray = colors
        return colors
    }

    //If the user wants to get a complementary harmony color-scheme and uses dark or light mode.
    fun complementary(color: Int, darkMode: Boolean?) : IntArray{
        val colors = IntArray(5)
        val comp = getComplementary(color)
        val dm = darkMode
        colors[0] = color
        colors[1] = Color.rgb(min(color.red + c2, 255), min(color.green + c2, 255), min(color.blue + c2, 255))
        if(dm != null){
            if(dm){
                //Make this color brighter instead of darker, when in dark mode.
                colors[2] = Color.rgb(max(color.red - c3_d, 0), max(color.green - c3_d, 0), max(color.blue - c3_d, 0))
                colors[3] = Color.rgb(min(comp.red + c4_d, 255), min(comp.green + c4_d, 255), min(comp. blue + c4_d, 255))
                colors[4] = Color.rgb(min(comp.red + c5_d, 255), min(comp.green + c5_d, 255), min(comp.blue + c5_d, 255))
            }else{
                colors[2] = Color.rgb(max(color.red - c3, 0), max(color.green - c3, 0), max(color.blue - c3, 0))
                colors[3] = Color.rgb(min(comp.red + c4, 255), min(comp.green + c4, 255), min(comp. blue + c4, 255))
                colors[4] = Color.rgb(max(comp.red - c5, 0), max(comp.green - c5, 0), max(comp.blue - c5, 0))
            }
        }else{
            colors[2] = Color.rgb(max(color.red - c3, 0), max(color.green - c3, 0), max(color.blue - c3, 0))
            colors[3] = Color.rgb(min(comp.red + c4, 255), min(comp.green + c4, 255), min(comp. blue + c4, 255))
            colors[4] = Color.rgb(max(comp.red - c5, 0), max(comp.green - c5, 0), max(comp.blue - c5, 0))
        }

        colorArray = colors
        return colors
    }

    //Returns the complementary color of a given color.
    private fun getComplementary(color: Int) : Int{
        val r = color.red
        val g = color.green
        val b = color.blue
        val d = max(max(r,g),b) + min(min(r,g),b)
        return Color.rgb(d - r, d - g, d - b)
    }
}