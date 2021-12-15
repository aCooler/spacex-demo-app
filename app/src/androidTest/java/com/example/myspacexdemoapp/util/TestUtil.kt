package com.example.myspacexdemoapp.util

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object TestUtil {
    fun <T : ViewModel> factoryFor(model: T): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(model.javaClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return model as T
                }
                throw IllegalArgumentException("unexpected model class $modelClass")
            }
        }
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    fun isRefreshing(): Matcher<View> {
        return object : BoundedMatcher<View, SwipeRefreshLayout>(
            SwipeRefreshLayout::class.java
        ) {
            override fun describeTo(description: Description) {
                description.appendText("is refreshing")
            }

            override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
                return view.isRefreshing
            }
        }
    }

    fun withTextColor(expectedId: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun matchesSafely(textView: TextView): Boolean {
                val colorId = ContextCompat.getColor(textView.context, expectedId)
                return textView.currentTextColor == colorId
            }

            override fun describeTo(description: Description) {
                description.appendText("with text color: ")
                description.appendValue(expectedId)
            }
        }
    }
}
