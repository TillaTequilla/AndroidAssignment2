package com.example.androidAssignment2.extension

import android.content.res.Resources
import android.graphics.Rect
import androidx.fragment.app.DialogFragment

fun DialogFragment.setSizePercent(percentageWidth: Int, percentageHeight: Int) {
    val percentWidth = percentageWidth.toFloat() / 100
    val percentHeight = percentageHeight.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val width = rect.width() * percentWidth
    val height = rect.height() * percentHeight
    dialog?.window?.setLayout(width.toInt(), height.toInt())
}