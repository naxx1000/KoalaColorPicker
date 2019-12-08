package com.rakiwow.koalacolorpickersample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rakiwow.koalacolorpicker.ColorToHarmonyColors
import com.rakiwow.koalacolorpicker.KoalaColorPicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var cursorPos: Int? = null
    private var mIsDarkMode: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_choose_color.setOnClickListener {
            val colorDialogFragment = KoalaColorPicker()
                .setTitle("Select a theme color") //Sets the title text of the dialog
                .setDialogBackgroundColor(Color.WHITE) //Sets the background color of the dialog
                .setTextColor(Color.DKGRAY) //Sets the color of the text in the dialog
                .setAcceptButtonColor(Color.LTGRAY) //Sets the color of the accept button
                .setHasDarkModeOption(true) //Enables or disables the switch for dark and light mode
                .setSwitchText("Enable Darkmode") //Sets the text above the dark mode switch
                //Sets the value of the dark mode switch when the user opens the dialog.
                //Get the value from the 'setOnSwitchClickListener'
                .setSwitchDefaultValue(mIsDarkMode)
                //Sets the position of the cursor when the user opens the dialog.
                //Get the cursor position from the 'setOnAcceptColorListener'
                .setCursorPosition(cursorPos)
                .setDarkModeIntensity(170) //Higher values will increase darkness in dark mode
                .setLightModeIntensity(170) //Higher values will increase lightness in light mode

            //Is called whenever the user touches the color spectrum view
            colorDialogFragment.setOnColorSelectedListener(object: KoalaColorPicker.OnColorSelectListener{
                override fun onColorSelect(colorInt: Int) {
                    //background.setBackgroundColor(colorInt)
                }
            })

            //Is called when the user click the dialog fragment button to accept a color
            colorDialogFragment.setOnAcceptColorListener(object: KoalaColorPicker.OnAcceptColorListener{
                override fun onAcceptColor(colorInt: Int, cursorPosition: Int?) {
                    background.setBackgroundColor(colorInt)
                    cursorPos = cursorPosition

                    setLayoutColors(colorInt, mIsDarkMode)
                }

            })

            //Is called when the user clicks the switch for darkmode colors
            colorDialogFragment.setOnSwitchClickListener(object: KoalaColorPicker.OnSwitchClickListener{
                override fun onSwitchClick(isDarkMode: Boolean?) {
                    mIsDarkMode = isDarkMode
                }
            })

            //Show the dialog fragment
            colorDialogFragment.show(supportFragmentManager, "ColorPickerDialogFragment")
        }
    }

    fun setLayoutColors(color: Int, isDarkMode: Boolean?){
        val colorToHarmonyColors = ColorToHarmonyColors()

        val colors = colorToHarmonyColors.complementary(color, mIsDarkMode)

        view1.setBackgroundColor(colors[0])
        background.setBackgroundColor(colors[1])
        view3.setBackgroundColor(colors[2])
        view4.setBackgroundColor(colors[3])
        view5.setBackgroundColor(colors[4])
    }
}
