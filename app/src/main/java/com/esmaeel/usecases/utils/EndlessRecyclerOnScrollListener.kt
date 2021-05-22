package com.esmaeel.usecases.utils

import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.

    //    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    private val visibleThreshold =
        1 // The minimum amount of items to have below your current scroll position before loading more.
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var currentVisiableItem = 0
    var findLastCompeletlyVisiblePosition = 0
    var currentPage = 1 // lets make it public to access it anywhere
    private var isLastItemDisplaying = false
    var mRecyclerViewHelper: RecyclerViewPositionHelper? = null
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView)
        findLastCompeletlyVisiblePosition =
            mRecyclerViewHelper!!.findLastCompletelyVisibleItemPosition()
        visibleItemCount = recyclerView.childCount
        totalItemCount = mRecyclerViewHelper!!.itemCount
        firstVisibleItem = mRecyclerViewHelper!!.findFirstVisibleItemPosition()
        currentVisiableItem = mRecyclerViewHelper!!.findLastVisibleItemPosition()
        isLastItemDisplaying = mRecyclerViewHelper!!.isLastItemDisplaying(recyclerView)
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached
            // Do something
            currentPage++
            onLoadMore(currentPage, currentVisiableItem)
            loading = true
        }
    }

    //Start loading
    abstract fun onLoadMore(currentPage: Int, lastVisibleItemPosition: Int)

    companion object {
        var TAG = "EndlessScrollListener"
    }

    fun reset() {
        currentPage = 1
    }
}