package com.example.ipt_final_project

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class SearchItem (
    private var itemList: List<String>,
            private val onClick: (String) -> Unit
)   : RecyclerView.Adapter<SearchItem.ViewHolder>() {
    private var filteredList: List<String> = itemList
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.textView.text = item
        holder.textView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            itemList
        } else {
            itemList.filter { it.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
    }

