package com.test.testtasknews.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.test.testtasknews.R

class LoadingDialog constructor(activity: Activity) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dialog)
        if (window != null) {
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setCancelable(false)
    }
}