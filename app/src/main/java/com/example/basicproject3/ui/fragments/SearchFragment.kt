package com.example.basicproject3.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.databinding.FragmentSearchBinding
import com.example.basicproject3.ui.adapters.CategoryListAdapter
import com.example.basicproject3.ui.adapters.EventListAdapter
import com.example.basicproject3.ui.viewmodels.SearchViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private var db = Firebase.firestore
    private val binding get() = _binding!!
    private val events: MutableList<Event> = mutableListOf()
    private val filteredEvents: MutableList<Event> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        //load category list
        searchViewModel.categoryList.observe(viewLifecycleOwner) {
            binding.recyclerViewCategories.adapter = CategoryListAdapter(it)
        }

        context?.let { onSearchListener(it) }
        //Clear focus from the search bar when clicked outside
        binding.searchPage.setOnClickListener {
            binding.searchBar.clearFocus()
            clearFilteredEventsAndAdapter()
        }

        loadEvents()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchBar.isFocused) {
                        binding.searchBar.clearFocus()
                        clearFilteredEventsAndAdapter()
                    } else if (binding.recyclerViewEvents.adapter != null) {
                        binding.recyclerViewEvents.adapter = null
                    } else findNavController().popBackStack()
                }
            })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        clearFilteredEventsAndAdapter()
    }

    private fun onSearchListener(context: Context) {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterEvents(newText)
                    binding.recyclerViewEvents.adapter = EventListAdapter(context, filteredEvents)
                } else {
                    clearFilteredEventsAndAdapter()
                }
                return false
            }
        })
    }

    private fun loadEvents() {
        val collectionRef = db.collection("events")
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            events.clear()
            for (document in querySnapshot) {
                val event = document.toObject(Event::class.java)
                events.add(event)
            }
            filteredEvents.clear()
        }
    }

    private fun clearFilteredEventsAndAdapter() {
        filteredEvents.clear()
        binding.recyclerViewEvents.adapter = null
    }

    private fun filterEvents(query: String) {
        filteredEvents.clear()
        for (event in events) if (event.title!!.lowercase().contains(query.lowercase())) {
            filteredEvents.add(event)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}