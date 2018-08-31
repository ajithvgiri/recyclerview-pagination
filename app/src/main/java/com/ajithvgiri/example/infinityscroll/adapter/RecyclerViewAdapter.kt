package com.ajithvgiri.example.infinityscroll.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.ajithvgiri.example.infinityscroll.R
import com.ajithvgiri.infinityscroll.RecyclerViewCallBack
import java.util.*

class RecyclerViewAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<String> = ArrayList()

    private var isLoadingAdded = false
    private var retryPageLoad = false

    private var recyclerViewCallBack: RecyclerViewCallBack

    var resultList: ArrayList<String>
        get() = list
        set(list) {
            this.list = list
        }

    val isEmpty: Boolean
        get() = itemCount == 0

    init {
        list = ArrayList()
        this.recyclerViewCallBack = context as RecyclerViewCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW -> {
                val viewItem = inflater.inflate(R.layout.layout_recyclerview_item, parent, false)
                viewHolder = ViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.layout_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listItem = list[position]

        when (getItemViewType(position)) {
            VIEW -> {
                val viewHolder = holder as ViewHolder
                viewHolder.textViewTitle.text = listItem
            }

            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                if (retryPageLoad) loadingViewHolder.progressBar.visibility = View.GONE
                else loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else VIEW
    }

    /**
     * Helpers - Pagination
     * _____________________________________________________________________________________________
     */

    fun add(string: String) {
        list.add(string)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(stringList: List<String>) {
        for (result in stringList) {
            add(result)
        }
    }

    fun remove(string: String?) {
        val position = list.indexOf(string)
        if (position > -1) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add("")
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = list.size - 1
        val result = getItem(position)

        if (result != null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): String? {
        return list[position]
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param isretry
     */
    fun showRetry(isretry: Boolean) {
        retryPageLoad = isretry
        notifyItemChanged(list.size - 1)
    }


    /**
     * View Holders
     * _____________________________________________________________________________________________
     */

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)

    }


    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    }

    companion object {
        // View Types
        private val VIEW = 0
        private val LOADING = 1

    }

}