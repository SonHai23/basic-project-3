package com.example.basicproject3.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicproject3.R
import com.example.basicproject3.data.model.OrganizersToFollow
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class OrganizersToFollowAdapter(private val organizers: ArrayList<OrganizersToFollow>) : RecyclerView.Adapter<OrganizersToFollowAdapter.OrganizerViewHolder>() {
    private val storage = FirebaseStorage.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizersToFollowAdapter.OrganizerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_organizer, parent, false)
        return OrganizerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrganizersToFollowAdapter.OrganizerViewHolder, position: Int) {
//        val currentItem = organizers[position]
        val currentItem : OrganizersToFollow = organizers[position]
//        val imageUrl = storage[position]
//        holder.organizerAvatar.setImageResource(currentItem.organizerAvatar)
        holder.organizerDescription.text = currentItem.name
        Glide.with(holder.itemView).load(currentItem.profiles).into(holder.organizerAvatar)
    }

    override fun getItemCount(): Int {
        return organizers.size
    }

    class OrganizerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val organizerAvatar : CircleImageView = itemView.findViewById(R.id.imgOrganizerAvatar)
        val organizerDescription : TextView = itemView.findViewById(R.id.txtOrganizerName)
    }
}