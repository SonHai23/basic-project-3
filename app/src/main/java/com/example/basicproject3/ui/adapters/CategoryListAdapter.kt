package com.example.basicproject3.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.Category

class CategoryListAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return CategoryViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
    }

    override fun getItemCount() = dataset.size
}