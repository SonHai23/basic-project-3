package com.example.basicproject3.ui.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicproject3.R
import com.example.basicproject3.TicketDetailsActivity
import com.example.basicproject3.data.Utils.Companion.formatDate
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.data.model.Ticket
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class TicketListAdapter(
    private val context: Context,
    private val dataset: List<Ticket>
) : RecyclerView.Adapter<TicketListAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)

        return TicketViewHolder(adapterLayout)
    }

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ShapeableImageView = view.findViewById(R.id.imgEvent)
        val textViewTitle: TextView = view.findViewById(R.id.txtEventTitle)
        val textViewTicketDuration: TextView = view.findViewById(R.id.txtEventStartTime)
    }

    override fun onBindViewHolder(holder: TicketListAdapter.TicketViewHolder, position: Int) {
        val ticket = dataset[position]
        val db = FirebaseFirestore.getInstance()
        db.collection("events").document(ticket.eid!!).get().addOnSuccessListener {
            val event = it.toObject<Event>()!!
            event.getImgUrl().addOnSuccessListener { it1 ->
                Glide.with(context).load(it1).into(holder.imgView)
            }
            holder.textViewTitle.text = event.title
            holder.textViewTicketDuration.text = formatDate(event.date_start)
            holder.itemView.setOnClickListener {
                val intent = Intent(context, TicketDetailsActivity::class.java)
                val bundle = Bundle().apply {
                    putParcelable("ticket", ticket)
                }
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = dataset.size
}