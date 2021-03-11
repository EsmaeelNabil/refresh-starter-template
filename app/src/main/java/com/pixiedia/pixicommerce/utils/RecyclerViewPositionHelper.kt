package com.pixiedia.pixicommerce.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

public class RecyclerViewPositionHelper internal constructor(val recyclerView: RecyclerView) {
    val layoutManager: RecyclerView.LayoutManager?

    /**
     * Returns the adapter item count.
     *
     * @return The total number on items in a layout manager
     */
    val itemCount: Int
        get() = layoutManager?.itemCount ?: 0

    /**
     * Returns the adapter position of the first visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the first visible item or [RecyclerView.NO_POSITION] if
     * there aren't any visible items.
     */
    fun findFirstVisibleItemPosition(): Int {
        val child =
            findOneVisibleChild(0, layoutManager!!.childCount, false, true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child
        )
    }

    /**
     * Returns the adapter position of the first fully visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the first fully visible item or
     * [RecyclerView.NO_POSITION] if there aren't any visible items.
     */
    fun findFirstCompletelyVisibleItemPosition(): Int {
        val child =
            findOneVisibleChild(0, layoutManager!!.childCount, true, false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child
        )
    }

    /**
     * Returns the adapter position of the last visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the last visible view or [RecyclerView.NO_POSITION] if
     * there aren't any visible items
     */
    fun findLastVisibleItemPosition(): Int {
        val child =
            findOneVisibleChild(layoutManager!!.childCount - 1, -1, false, true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child
        )
    }

    fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {

            when (recyclerView.layoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions =
                        (recyclerView.layoutManager as StaggeredGridLayoutManager?)!!.findLastCompletelyVisibleItemPositions(
                            null
                        )

                    if (lastVisibleItemPositions != null && lastVisibleItemPositions.isNotEmpty() && lastVisibleItemPositions[0] != RecyclerView.NO_POSITION
                        && lastVisibleItemPositions[0] == recyclerView.adapter!!.itemCount - 1
                    ) return true

                }
                is LinearLayoutManager -> {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                            .getItemCount() - 1
                    ) return true

                }
            }

        }
        return false
    }

    /**
     * Returns the adapter position of the last fully visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the last fully visible view or
     * [RecyclerView.NO_POSITION] if there aren't any visible items.
     */
    fun findLastCompletelyVisibleItemPosition(): Int {
        val child =
            findOneVisibleChild(layoutManager!!.childCount - 1, -1, true, false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child
        )
    }

    fun findOneVisibleChild(
        fromIndex: Int, toIndex: Int, completelyVisible: Boolean,
        acceptPartiallyVisible: Boolean
    ): View? {
        val helper: OrientationHelper
        helper = if (layoutManager!!.canScrollVertically()) {
            OrientationHelper.createVerticalHelper(layoutManager)
        } else {
            OrientationHelper.createHorizontalHelper(layoutManager)
        }
        val start = helper.startAfterPadding
        val end = helper.endAfterPadding
        val next = if (toIndex > fromIndex) 1 else -1
        var partiallyVisible: View? = null
        var i = fromIndex
        while (i != toIndex) {
            val child = layoutManager.getChildAt(i)
            val childStart = helper.getDecoratedStart(child)
            val childEnd = helper.getDecoratedEnd(child)
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child
                    }
                } else {
                    return child
                }
            }
            i += next
        }
        return partiallyVisible
    }

    companion object {
        fun createHelper(recyclerView: RecyclerView?): RecyclerViewPositionHelper {
            if (recyclerView == null) {
                throw NullPointerException("Recycler View is null")
            }
            return RecyclerViewPositionHelper(recyclerView)
        }
    }

    init {
        layoutManager = recyclerView.layoutManager
    }
}