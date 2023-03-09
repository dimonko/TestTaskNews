package com.test.testtasknews.ui.base

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.test.testtasknews.R

abstract class ActivityEx : AppCompatActivity() {

    fun showSnackBarError(text: String) {
        val snack = Snackbar.make(
            findViewById(android.R.id.content),
            text,
            Snackbar.LENGTH_LONG
        )
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.red_color))
        val texView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        texView.setTextColor(Color.WHITE)
        texView.setTypeface(texView.typeface, Typeface.BOLD)
        snack.show()
    }

}