package com.example.rese_yl.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class RSYButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont(){

        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}