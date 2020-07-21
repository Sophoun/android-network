package com.sophoun.generic.adapter

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.core.framework.adapter.BaseRecyclerViewHolder
import org.jetbrains.anko.childrenRecursiveSequence

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private lateinit var onBottomReachedListener: (position: Int) -> Unit
    private lateinit var onItemClickListener: (view: View, position: Int, item: T) -> Unit
    private lateinit var onItemLongClickListener: (view: View, position: Int, item: T) -> Boolean
    private lateinit var onItemTouchListener: (event: MotionEvent, view: View, position: Int, item: T) -> Boolean

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
    }

    fun appendItems(items: List<T>) {
        this.items.addAll(items)
    }

    fun clearItems() {
        this.items.clear()
    }

    /**
     * Attach ViewHolder to adapter
     */
    abstract fun attachViewHolder(parent: ViewGroup, viewType: Int) : BaseRecyclerViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<T> {
        return attachViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T>, position: Int) {
        handleBottomReachedListener(position)
        // bind item to each view
        val item = items[position]
        holder.bind(item)
        // set click listener to child view from recycler adapter
        holder.itemView.childrenRecursiveSequence().forEach { view ->
            if (::onItemClickListener.isInitialized) {
                view.setOnClickListener {
                    handleOnItemClickListener(it, position, item)
                }
            }
        }
        // set long click listener to child view from recycler adapter
        holder.itemView.childrenRecursiveSequence().forEach { view ->
            if (::onItemLongClickListener.isInitialized) {
                view.setOnLongClickListener {
                    handleOnItemLongClickListener(it, position, item)
                }
            }
        }
        // set touch listener to child view from recycler adapter
        holder.itemView.childrenRecursiveSequence().forEach { view ->
            if (::onItemTouchListener.isInitialized) {
                view.setOnTouchListener { _, event ->
                    return@setOnTouchListener handleOnItemTouchListener(event, view, position, item)
                }
            }
        }
    }

    /**
     * Listen when adapter bind the latest item to view.
     * It means it was reached the bottom of recycler view.
     */
    fun addOnBottomReachedListener(onBottomReachedListener: (position: Int) -> Unit) {
        this.onBottomReachedListener = onBottomReachedListener
    }

    /**
     * Check and invoke [onBottomReachedListener] method
     * when adapter bind the latest item to recycler view.
     */
    private fun handleBottomReachedListener(position: Int) {
        if (items.size - 1 == position) {
            if (::onBottomReachedListener.isInitialized) {
                onBottomReachedListener(position)
            }
        }
    }

    /**
     * Add callback for item click
     */
    fun addOnItemClickListener(onItemClickListener: (view: View, position: Int, item: T) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private fun handleOnItemClickListener(view: View, position: Int, item: T) {
        onItemClickListener(view, position, item)
    }

    /**
     * Add callback for item long click
     */
    fun addOnItemLongClickListener(onItemLongClickListener: (view: View, position: Int, item: T) -> Boolean) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    private fun handleOnItemLongClickListener(view: View, position: Int, item: T): Boolean {
        return onItemLongClickListener(view, position, item)
    }

    /**
     * Add callback for item touch.
     *
     * Note:
     *
     * - Add listener and always return [false]
     * to the listener if you want adapter to invoke
     * [onItemClickListener] or [onItemLongClickListener]
     */
    fun addOnItemTouchListener(onItemTouchListener: (event: MotionEvent, view: View, position: Int, item: T) -> Boolean) {
        this.onItemTouchListener = onItemTouchListener
    }

    private fun handleOnItemTouchListener(event: MotionEvent, view: View, position: Int, item: T): Boolean {
        return this.onItemTouchListener(event, view, position, item)
    }
}