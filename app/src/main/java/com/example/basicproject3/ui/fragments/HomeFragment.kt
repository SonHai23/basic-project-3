package com.example.basicproject3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.basicproject3.R
import com.example.basicproject3.data.model.OrganizersToFollow
import com.example.basicproject3.data.model.PopularEvents
import com.example.basicproject3.databinding.FragmentHomeBinding
import com.example.basicproject3.ui.adapters.HomeTabLayoutAdapter
import com.example.basicproject3.ui.adapters.OrganizersToFollowAdapter
import com.example.basicproject3.ui.adapters.PopularEventsAdapter
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: HomeTabLayoutAdapter
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<OrganizersToFollow>
    lateinit var imageID: Array<Int>
    lateinit var heading: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //ask for close app if press back button in home fragment, if in the next 5s, user pressed back button the second time, close app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        adapter = HomeTabLayoutAdapter(requireActivity().supportFragmentManager, lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Popular"))
        tabLayout.addTab(tabLayout.newTab().setText("Happening"))

        viewPager2.adapter = adapter

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

        imageID = arrayOf(
            R.drawable.edm1,
            R.drawable.edm2,
            R.drawable.edm3,
            R.drawable.edm4,
            R.drawable.edm5,
            R.drawable.edm6,
            R.drawable.edm7,
            R.drawable.edm8,
            R.drawable.edm9,
        )

        heading = arrayOf(
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
        )

        newRecyclerView = binding.rvOrganizersToFollow
        newRecyclerView.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<OrganizersToFollow>()
        getOrganizersToFollow()

        return binding.root
    }

    private fun getOrganizersToFollow() {
        for (i in imageID.indices) {
            val organizersToFollow = OrganizersToFollow(imageID[i], heading[i])
            newArrayList.add(organizersToFollow)
        }
        newRecyclerView.adapter = OrganizersToFollowAdapter(newArrayList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}