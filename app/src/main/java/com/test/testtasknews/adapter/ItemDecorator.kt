package com.test.testtasknews.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(
    private val top: Int,
    private val bot: Int,
    private val start: Int,
    private val end: Int
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = top
        outRect.left = start
        outRect.right = end
        outRect.bottom = bot
    }
}