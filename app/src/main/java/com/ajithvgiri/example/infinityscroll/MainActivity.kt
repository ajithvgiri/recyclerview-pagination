package com.ajithvgiri.example.infinityscroll

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ajithvgiri.example.infinityscroll.adapter.RecyclerViewAdapter
import com.ajithvgiri.infinityscroll.RecyclerViewCallBack
import com.ajithvgiri.infinityscroll.RecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewCallBack {


    /**
     * Variables used for Pagination
     */
    private var currentPage = 0
    private val totalPages = 100
    private var loading = false
    private var ilastPage = false


    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerViewAdapter = RecyclerViewAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.addOnScrollListener(object : RecyclerViewScrollListener(linearLayoutManager) {

            override val isLastPage: Boolean = ilastPage
            override var isLoading: Boolean = loading
            override fun loadNextPage() {
                loading = true
                currentPage += 1
                loadScrollPage()
            }
        })

        loadScrollPage()
    }

    private fun loadScrollPage() {
        var i = 0
        while (i < 10) {
            recyclerViewAdapter.add("$i")
            i++
        }
    }

    override fun retry() {
        loadScrollPage()
    }
}
