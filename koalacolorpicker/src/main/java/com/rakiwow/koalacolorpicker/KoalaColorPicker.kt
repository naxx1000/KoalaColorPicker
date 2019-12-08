package com.rakiwow.koalacolorpicker

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.color_picker_dialog_fragment.view.*
import kotlin.math.max
import kotlin.math.min

class KoalaColorPicker : DialogFragment(){

    private var listenerColorSelect: OnColorSelectListener? = null
    private var listenerColorAccept: OnAcceptColorListener? = null
    private var listenerSwitchClick: OnSwitchClickListener? = null
    private var selectedColor: Int = Color.rgb(0,255, 255)
    private var title: String? = null
    private var switchText: String? = null
    private var hasDarkModeOption: Boolean? = null
    private var switchDefaultValue: Boolean? = null
    private var textColor: Int? = null
    private var colorBackground: Int? = null
    private var acceptButtonColor: Int? = null
    private var cursorPos: Int? = null
    private var lightModeIntensity: Int = 0
    private var darkModeIntensity: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.color_picker_dialog_fragment, container, false)

        //OnColorChosenListener which updates when the user ColorPickerView is created and when the
        //user touches the ColorPickerView.
        view.colorPicker.setCursorPos(cursorPos).setOnColorChosenListener(object: ColorPickerView.OnColorChosenListener{
            override fun onColorChosen(color: Int, cursorPosition: Int) {
                selectedColor = color
                cursorPos = cursorPosition
                listenerColorSelect?.onColorSelect(color)
            }
        })

        //The dark mode switch's OnClickListener.
        view.switchDarkLight.setOnClickListener {
            listenerSwitchClick?.onSwitchClick(view.switchDarkLight.isChecked)
        }

        //The dialog button's OnClickListener that accepts the chosen color by the user.
        view.buttonAccept.setOnClickListener {
            val option = hasDarkModeOption
            if(option != null){
                if(option){
                    //If dark mode is true; fire onAcceptColor function, with a dark mode color,
                    //else send light mode color instead.
                    if(view.switchDarkLight.isChecked){
                        listenerColorAccept?.onAcceptColor(Color.rgb(
                            max(selectedColor.red - darkModeIntensity, 0),
                            max(selectedColor.green - darkModeIntensity, 0),
                            max(selectedColor.blue - darkModeIntensity, 0)),
                            cursorPos)
                    }else{
                        listenerColorAccept?.onAcceptColor(Color.rgb(
                            min(selectedColor.red + lightModeIntensity,255),
                            min(selectedColor.green + lightModeIntensity, 255),
                            min(selectedColor.blue + lightModeIntensity, 255)),
                            cursorPos)
                    }
                }else{
                    listenerColorAccept?.onAcceptColor(selectedColor, cursorPos)
                }
            }else{
                //If the darkModeOption does not exist, use full colors.
                listenerColorAccept?.onAcceptColor(selectedColor, cursorPos)
            }
            dismiss() //Closes the dialog fragment
        }

        //If the title text is null, hide the text view, otherwise show the title with text.
        if (title == null){
            view.tvTitle.visibility = View.GONE
        }else{
            view.tvTitle.visibility = View.VISIBLE
            view.tvTitle.text = title
        }

        //Set the background of the fragment layout.
        val cb = colorBackground
        if(cb != null){
            view.dialogLayout.setBackgroundColor(cb)
        }

        //Sets the text color of the fragment layout.
        val tc = textColor
        if(tc != null){
            view.tvTitle.setTextColor(tc)
        }

        //Sets the color of the accept button of the fragment layout.
        val abc = acceptButtonColor
        if(abc != null){
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL
            shape.setColor(abc)
            view.buttonAccept.background = shape
        }

        //If the hasDarkModeOption is true, then the switch will be shown in the fragment.
        // Otherwise it is hidden.
        val option = hasDarkModeOption
        if(option != null){
            if(option){
                view.switchDarkLight.visibility = View.VISIBLE
                if(switchDefaultValue != null){
                    view.switchDarkLight.isChecked = switchDefaultValue as Boolean
                }
                if (switchText != null){
                    view.switchTextView.visibility = View.VISIBLE
                    view.switchTextView.text = switchText
                    if(tc != null){
                        view.switchTextView.setTextColor(tc)
                    }
                }
            }
        }

        return view
    }

    /**
     * Factory Methods ----------------------------------------------
     */

    fun setLightModeIntensity(value: Int) : KoalaColorPicker{
        this.lightModeIntensity = value
        return this
    }

    fun setDarkModeIntensity(value: Int) : KoalaColorPicker{
        this.darkModeIntensity = value
        return this
    }

    fun setSwitchDefaultValue(value: Boolean?) : KoalaColorPicker{
        this.switchDefaultValue = value
        return this
    }

    fun setDialogBackgroundColor(colorInt: Int) : KoalaColorPicker{
        this.colorBackground = colorInt
        return this
    }

    fun setAcceptButtonColor(colorInt: Int) : KoalaColorPicker{
        this.acceptButtonColor = colorInt
        return this
    }

    fun setTextColor(colorInt: Int) : KoalaColorPicker{
        this.textColor = colorInt
        return this
    }

    fun setTitle(title: String) : KoalaColorPicker{
        this.title = title
        return this
    }

    fun setSwitchText(text: String) : KoalaColorPicker{
        this.switchText = text
        return this
    }

    fun setHasDarkModeOption(hasOption: Boolean) : KoalaColorPicker{
        this.hasDarkModeOption = hasOption
        return this
    }

    fun setCursorPosition(pos: Int?) : KoalaColorPicker{
        this.cursorPos = pos
        return this
    }

    /**
     * Listeners ---------------------------------------------------
     */

    fun setOnColorSelectedListener(listener: OnColorSelectListener){
        listenerColorSelect = listener
    }

    fun setOnAcceptColorListener(listener: OnAcceptColorListener){
        listenerColorAccept = listener
    }

    fun setOnSwitchClickListener(listener: OnSwitchClickListener){
        listenerSwitchClick = listener
    }

    interface OnColorSelectListener{
        fun onColorSelect(colorInt: Int)
    }

    interface OnAcceptColorListener{
        fun onAcceptColor(colorInt: Int, cursorPosition: Int?)
    }

    interface OnSwitchClickListener{
        fun onSwitchClick(isDarkMode: Boolean?)
    }
}