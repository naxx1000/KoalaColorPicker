package com.rakiwow.koalacolorpicker

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.math.max
import kotlin.math.min

class ColorToHarmonyColors {

    fun complementary(color: Int) : IntArray{
        val colors = IntArray(5)
        colors[0] = color
        colors[1] = Color.rgb(min(color.red + 45, 255), min(color.green + 45, 255), min(color.blue + 45, 255))
        colors[2] = Color.rgb(max(color.red - 50, 0), max(color.green - 50, 0), max(color.blue - 50, 0))
        val comp = getComplementary(color)
        colors[3] = Color.rgb(min(comp.red + 25, 255), min(comp.green + 25, 255), min(comp. blue + 25, 255))
        colors[4] = Color.rgb(max(comp.red - 120, 0), max(comp.green - 120, 0), max(comp.blue - 120, 0))

        return colors
    }

    fun complementary(color: Int, darkMode: Boolean?) : IntArray{
        val colors = IntArray(5)
        colors[0] = color
        colors[1] = Color.rgb(min(color.red + 45, 255), min(color.green + 45, 255), min(color.blue + 45, 255))
        colors[2] = Color.rgb(max(color.red - 50, 0), max(color.green - 50, 0), max(color.blue - 50, 0))
        val comp = getComplementary(color)
        colors[3] = Color.rgb(min(comp.red + 25, 255), min(comp.green + 25, 255), min(comp. blue + 25, 255))
        val dm = darkMode
        if(dm != null){
            if(dm){
                colors[4] = Color.rgb(min(comp.red + 130, 255), min(comp.green + 130, 255), min(comp.blue + 130, 255))
            }else{
                colors[4] = Color.rgb(max(comp.red - 120, 0), max(comp.green - 120, 0), max(comp.blue - 120, 0))
            }
        }else{
            colors[4] = Color.rgb(max(comp.red - 120, 0), max(comp.green - 120, 0), max(comp.blue - 120, 0))
        }

        return colors
    }

    private fun getComplementary(color: Int) : Int{
        val r = color.red
        val g = color.green
        val b = color.blue
        val d = max(max(r,g),b) + min(min(r,g),b)
        return Color.rgb(d - r, d - g, d - b)
    }
}