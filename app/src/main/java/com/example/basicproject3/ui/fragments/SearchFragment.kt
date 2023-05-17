package com.example.basicproject3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.basicproject3.databinding.FragmentSearchBinding
import com.example.basicproject3.ui.adapters.CategoryListAdapter
import com.example.basicproject3.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        //load category list
        lifecycleScope.launch {
            binding.progressBarCategory.visibility = View.VISIBLE
            val categoryList = searchViewModel.getCategoryList()
            val recyclerView = binding.recyclerViewCategories
//            val eventList = searchViewModel.getEventsByName()
            recyclerView.adapter = CategoryListAdapter(categoryList)
//            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    binding.searchBar.clearFocus()
//                    if (eventList != null) {
//                        binding.recyclerViewEvents.adapter = EventListAdapter(activity, )
//                    }
//                }
//
//                override fun onQueryTextChange(p0: String?): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//
//            })
            binding.progressBarCategory.visibility = View.GONE
        }
        //Clear focus from the search bar when clicked outside
        binding.searchPage.setOnClickListener { binding.searchBar.clearFocus() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}