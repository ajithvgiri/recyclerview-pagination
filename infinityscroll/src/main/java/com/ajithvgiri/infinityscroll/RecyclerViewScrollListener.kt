package com.ajithvgiri.infinityscroll

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class RecyclerViewScrollListener : RecyclerView.OnScrollListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    /**
     * LinearLayoutManager
     *
     * @param linearLayoutManager
     */
    constructor(linearLayoutManager: LinearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager
    }

    /**
     * GridLayoutManager
     *
     * @param gridLinearLayoutManager
     */
    constructor(gridLinearLayoutManager: GridLayoutManager) {
        this.gridLayoutManager = gridLinearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (linearLayoutManager != null) {
            val visibleCount = linearLayoutManager!!.childCount
            val totalCount = linearLayoutManager!!.itemCount
            val firstVisibleItemPosition = linearLayoutManager!!.findFirstVisibleItemPosition()
            if (!isLoading && !isLastPage) {
                if (visibleCount + firstVisibleItemPosition >= totalCount && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }
            }
        } else if (gridLayoutManager != null) {
            val visibleCount = gridLayoutManager!!.childCount
            val totalCount = gridLayoutManager!!.itemCount
            val firstVisibleItemPosition = gridLayoutManager!!.findFirstVisibleItemPosition()
            if (!isLoading && !isLastPage) {
                if (visibleCount + firstVisibleItemPosition >= totalCount && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }
            }
        }

    }

    protected abstract fun loadNextPage()

}

