package com.example.basicproject3.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.OrganizersToFollow
import com.google.android.material.imageview.ShapeableImageView
import de.hdodenhof.circleimageview.CircleImageView

class OrganizersToFollowAdapter(private val organizers: ArrayList<OrganizersToFollow>) : RecyclerView.Adapter<OrganizersToFollowAdapter.OrganizerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_organizer, parent, false)
        return OrganizerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrganizerViewHolder, position: Int) {
        val currentItem = organizers[position]
//        holder.organizerAvatar.setImageResource(currentItem.organizerAvatar)
        holder.organizerDescription.text = currentItem.organizerDescription
    }

    override fun getItemCount(): Int {
        return organizers.size
    }

    class OrganizerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val organizerAvatar : CircleImageView = itemView.findViewById(R.id.imgOrganizerAvatar)
        val organizerDescription : TextView = itemView.findViewById(R.id.txtOrganizerName)
    }
}