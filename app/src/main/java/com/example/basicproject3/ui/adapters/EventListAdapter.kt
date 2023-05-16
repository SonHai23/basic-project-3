package com.example.basicproject3.ui.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.EventActivity
import com.example.basicproject3.R
import com.example.basicproject3.data.model.Event

class EventListAdapter(
    private val context: Activity,
    private val category: String,
    private val dataset: List<Event>
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.event_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)

        return EventViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = dataset[position]
        holder.textView.text = event.title
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, EventActivity::class.java).apply {
                putExtras(bundleOf("category" to category, "eventId" to event.id))
            })
        }
    }

    override fun getItemCount() = dataset.size
}