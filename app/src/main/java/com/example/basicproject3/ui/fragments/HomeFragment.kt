package com.example.basicproject3.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.basicproject3.R
import com.example.basicproject3.data.model.OrganizersToFollow
import com.example.basicproject3.data.model.PopularEvents
import com.example.basicproject3.data.model.User
import com.example.basicproject3.databinding.FragmentHomeBinding
import com.example.basicproject3.ui.adapters.HomeTabLayoutAdapter
import com.example.basicproject3.ui.adapters.OrganizersToFollowAdapter
import com.example.basicproject3.ui.adapters.PopularEventsAdapter
import com.example.basicproject3.ui.viewmodels.HomeViewModel
import com.example.basicproject3.ui.viewmodels.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabAdapter: HomeTabLayoutAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var organizerList: ArrayList<OrganizersToFollow>
    private lateinit var oAdapter: OrganizersToFollowAdapter
    private var data = Firebase.firestore
    lateinit var imageID: Array<Int>
    lateinit var heading: Array<String>
//    private lateinit var organizerList: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //ask for close app if press back button in home fragment, if in the next 5s, user pressed back button the second time, close app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        tabAdapter = HomeTabLayoutAdapter(requireActivity().supportFragmentManager, lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Popular"))
        tabLayout.addTab(tabLayout.newTab().setText("Happening"))

        viewPager2.adapter = tabAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab!!.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        recyclerView = binding.rvOrganizersToFollow
        recyclerView.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true) //

        organizerList = arrayListOf()
       /* oAdapter = OrganizersToFollowAdapter(organizerList)
        recyclerView.adapter = oAdapter*/

        getOrganizersToFollow()

        return binding.root
    }

    private fun getOrganizersToFollow() {
        /*for (i in imageID.indices) {
            val organizersToFollow = OrganizersToFollow(imageID[i], heading[i])
            organizerList.add(organizersToFollow)
        }*/

        data = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()

        data.collection("users").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val organizer: OrganizersToFollow? = data.toObject(OrganizersToFollow::class.java)
                        if (organizer != null) {
                            val imagePath = "profiles/${data.id}"
                            val imageRef = storage.getReference(imagePath)
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                organizer.profiles = uri.toString()
                                organizerList.add(organizer)
                                recyclerView.adapter = OrganizersToFollowAdapter(organizerList)
                            }
//                            organizerList.add(organizer)
//                            Toast.makeText(activity, "$organizer", Toast.LENGTH_SHORT).show()
                        }
                    }
//                    recyclerView.adapter = OrganizersToFollowAdapter(organizerList) // Danh cho viec xu ly khi chua xu ly image tuwf storage
                }
            }
            .addOnFailureListener() {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }

//        recyclerView.adapter = OrganizersToFollowAdapter(organizerList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}