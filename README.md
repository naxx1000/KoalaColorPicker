# KoalaColorPicker
is a module that uses a custom AndroidX DialogFragment.

## Add dependency with JitPack

<img src="https://i.imgur.com/pflwQKV.png" data-canonical-src="https://i.imgur.com/pflwQKV.png"
width="400" height="331" />


## Full color spectrum!

<img src="https://imgur.com/r8cOkea.png" data-canonical-src="https://imgur.com/r8cOkea.png"
width="200" height="185" />

The user can see the full color spectrum and choose whichever color he/she wants.

<img src="https://imgur.com/rvPpNfp.png" data-canonical-src="https://imgur.com/rvPpNfp.png"
width="200" height="185" />

Drag and hold to see which color is chosen.


When enabling dark mode, the color that the user chooses are either lighter or darker, depending on the state of the switch.
This, however need to be enabled first.

```kotlin
val koalaPickerFragment = KoalaColorPicker()
	.setTitle("TitleString")
	.setHasDarkModeOption("True") //Will allow the option to switch to dark mode
	.setDarkModeIntensity(0-255)
	.setLightModeIntensity(0-255)
```


This enables light or dark mode versions of the colors chosen. This is useful for when the user will need to choose a color theme for the application.

```kotlin
val koalaPickerFragment = KoalaColorPicker()
	.setSwitchDefaultValue(isDarkMode)
	.setCursorPosition(cursorPosition)
```


To retrieve the color, use the 'setOnColorSelectedListener' to listen to click of the dialog accept button.

```kotlin
koalaPickerFragment.setOnColorSelectedListener(object: KoalaColorPicker.OnColorSelectListener{
    override fun onColorSelect(colorInt: Int) {
        someView.setBackgroundColor(colorInt)
    }
})
```


To remember the position of the cursor and the state of the switch, use the '.setOnAcceptColorListener' and 'setOnSwitchClickListener'.

```kotlin
private var cursorPos: Int? = null
private var mIsDarkMode: Boolean? = null

koalaPickerFragment.setOnAcceptColorListener(...
	override fun onAcceptColor(colorInt: Int, cursorPosition: Int?){...
		//Save this and use in the object creating functions below
		cursorPos = cursorPosition
	}
)

koalaPickerFragment.setOnSwitchClickListener(...
	override fun onSwitchClick(isDarkMode: Boolean?){...
		//Save this and use in the object creating functions below
		mIsDarkMode = isDarkMode
	}
)

val koalaPickerFragment = KoalaColorPicker()
	//Will put the dark mode switch to the same position, as when a color was chosen.
	.setSwitchDefaultValue(mIsDarkMode)
	//Will put the cursor to the same position, as when a color was chosen.
	.setCursorPosition(cursorPos)
```

## Easily create beautiful color schemes

<img src="https://imgur.com/hAaZ2p3.png" data-canonical-src="https://imgur.com/hAaZ2p3.png"
width="280" height="498" />

An additional class can be used to create color-scheme of five different colors by inputting one color. As of now it can create complementary harmony colors.
This is useful with the color picker as it can easily create a beautiful color theme for the application without much effort, and you can tweak the dark and light intensity to your liking.

The above figure shows an example of a color scheme when the user has chosen red as the color and he/she uses light mode.


## How to retrieve the complementary harmony color scheme

Input a color into the .complementary function, which returns a IntArray of size five with the new colors.

```kotlin
val colorToHarmonyColors = ColorToHarmonyColors()

//Get a 5 colors color-scheme which includes complementary colors.
val colors = colorToHarmonyColors.complementary(color, mIsDarkMode)

someView1.setBackgroundColor(colors[0])
someBackground.setBackgroundColor(colors[1])
someView3.setBackgroundColor(colors[2])
someView4.setBackgroundColor(colors[3])
someView5.setBackgroundColor(colors[4])
```

## Sample

The repository contains a sample that uses this library. Look for 'koalacolorpicker-sample'.

### Thank you

For having a look at this library. This is my first library that I have created and any input would be greatly appreciated.

My discord: Raki#5009

<img src="https://imgur.com/jQ4c3Sx.png" data-canonical-src="https://imgur.com/jQ4c3Sx.png"
width="209" height="185" />
