package com.example.basicproject3.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.basicproject3.data.model.Event
import com.example.basicproject3.databinding.FragmentSearchBinding
import com.example.basicproject3.ui.adapters.CategoryListAdapter
import com.example.basicproject3.ui.adapters.EventListAdapter
import com.example.basicproject3.ui.viewmodels.SearchViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            binding.progressBarCategory.visibility = View.VISIBLE
            val categoryList = searchViewModel.getCategoryList()
            val recyclerViewCategory = binding.recyclerViewCategories
            recyclerViewCategory.adapter = CategoryListAdapter(categoryList)
            binding.progressBarCategory.visibility = View.GONE
        }

        context?.let { onSearchListener(it) }
        //Clear focus from the search bar when clicked outside
        binding.searchPage.setOnClickListener {
            binding.searchBar.clearFocus()
            clearFilteredEventsAndAdapter()
        }

        loadEvents()

        return binding.root
    }

    private fun onSearchListener(context: Context) {

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterEvents(newText)
                    binding.recyclerViewEvents.adapter = EventListAdapter(context, filteredEvents)
                } else {
                    clearFilteredEventsAndAdapter()
                }
                return true
            }
        })
    }

    private fun loadEvents() {
        val collectionRef = db.collection("events")
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            events.clear()
            for (document in querySnapshot) {
                val book = document.toObject(Event::class.java)
                events.add(book)
            }
            filterEvents("")
        }
    }

    private fun clearFilteredEventsAndAdapter() {
        filteredEvents.clear()
        binding.recyclerViewEvents.adapter = EventListAdapter(requireContext(), filteredEvents)
    }

//    fun reloadEventList(context: Context, query: String?) {
//        val collectionRef = db.collection("events")
//        if (query != null) {
//            collectionRef.whereGreaterThanOrEqualTo("title", query)
//                .whereLessThanOrEqualTo("title", query + "\uF7FF").get().addOnSuccessListener {
//                    val eventList = mutableListOf<Event>()
//                    for (document in it) {
//                        eventList.add(document.toObject(Event::class.java))
//                    }
//                    binding.recyclerViewEvents.adapter = EventListAdapter(context, eventList)
//                }
//        }
//    }

    private fun filterEvents(query: String) {
        filteredEvents.clear()

        if (query.isEmpty()) {
            filteredEvents.addAll(events)
        } else {
            for (event in events) if (event.title!!.lowercase().contains(query.lowercase())) {
                filteredEvents.add(event)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}