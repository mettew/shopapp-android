package com.shopapp.ui.base.lce.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.shopapp.R
import kotlinx.android.synthetic.main.view_lce_error.view.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class LceErrorViewTest {

    private lateinit var context: Context
    private lateinit var view: LceErrorView

    @Before
    fun setUpTest() {
        context = RuntimeEnvironment.application.baseContext
        view = LceErrorView(context)
    }

    @Test
    fun shouldViewBeVisibleWithCorrectDataWhenContentError() {
        view.changeState(LceLayout.LceState.ErrorState(false))
        assertEquals(View.VISIBLE, view.visibility)

        assertEquals(context.getString(R.string.default_error), view.errorMessage.text.toString())

        assertEquals(ContextCompat.getDrawable(context, R.drawable.ic_sentiment_very_dissatisfied),
            view.errorImage.drawable)
    }

    @Test
    fun shouldViewBeVisibleWithCorrectDataWhenNetworkError() {
        view.changeState(LceLayout.LceState.ErrorState(true))
        assertEquals(View.VISIBLE, view.visibility)

        assertEquals(context.getString(R.string.network_connection_error), view.errorMessage.text.toString())

        assertEquals(ContextCompat.getDrawable(context, R.drawable.ic_no_signal),
            view.errorImage.drawable)
    }

    @Test
    fun shouldViewBeGone() {
        view.changeState(LceLayout.LceState.ContentState)
        assertEquals(View.GONE, view.visibility)

        view.changeState(LceLayout.LceState.EmptyState)
        assertEquals(View.GONE, view.visibility)

        view.changeState(LceLayout.LceState.LoadingState())
        assertEquals(View.GONE, view.visibility)
    }
}