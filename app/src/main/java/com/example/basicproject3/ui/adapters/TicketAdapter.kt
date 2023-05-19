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
import com.example.basicproject3.data.model.MyEvent
import com.example.basicproject3.data.model.Ticket
import com.google.android.material.imageview.ShapeableImageView

class TicketAdapter(private val tickets: ArrayList<HappeningEvent>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return TicketViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketAdapter.TicketViewHolder, position: Int) {
//        val currentItem = items[position]
        val currentItem : HappeningEvent = tickets[position]
        holder.eventTitle.text = currentItem.title
        Glide.with(holder.itemView).load(currentItem.events).into(holder.imageEvent)
        holder.dateStart.text = Utils.formatDate(currentItem.date_start)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageEvent : ShapeableImageView = itemView.findViewById(R.id.imgEvent)
        val eventTitle : TextView = itemView.findViewById(R.id.txtEventTitle)
        val dateStart : TextView = itemView.findViewById(R.id.txtEventStartTime)
    }
}
