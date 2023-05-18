package com.example.basicproject3.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicproject3.R
import com.example.basicproject3.data.Utils
import com.example.basicproject3.data.model.HappeningEvent
import com.example.basicproject3.data.model.OrganizersToFollow
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.storage.FirebaseStorage

class HappeningEventAdapter(private val items: ArrayList<HappeningEvent>) : RecyclerView.Adapter<HappeningEventAdapter.HappeningViewHolder>() {
    private val storage = FirebaseStorage.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappeningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return HappeningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HappeningViewHolder, position: Int) {
//        val currentItem = items[position]
        val currentItem : HappeningEvent = items[position]
        holder.eventTitle.text = currentItem.title
        Glide.with(holder.itemView).load(currentItem.events).into(holder.imageEvent)
//        holder.dateStart.text = currentItem.date_start
        holder.dateStart.text = Utils.formatDate(currentItem.date_start)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HappeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageEvent : ShapeableImageView = itemView.findViewById(R.id.imgEvent)
        val eventTitle : TextView = itemView.findViewById(R.id.txtEventTitle)
        val dateStart : TextView = itemView.findViewById(R.id.txtEventStartTime)
    }
}