package com.example.basicproject3.ui.adapters

import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.PopularEvents
import com.google.android.material.imageview.ShapeableImageView

class PopularEventsAdapter(private val items: ArrayList<PopularEvents>) : RecyclerView.Adapter<PopularEventsAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_event_popular, parent, false)
        return PopularViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val currentItem = items[position]
        holder.imageEvent.setImageResource(currentItem.imageEvent)
        holder.eventTitle.text = currentItem.eventTitle
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageEvent : ShapeableImageView = itemView.findViewById(R.id.imgEvent)
        val eventTitle : TextView = itemView.findViewById(R.id.txtEventTitle)
    }
}