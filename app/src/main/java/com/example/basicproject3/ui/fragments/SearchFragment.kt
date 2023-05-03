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
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val categoryList = searchViewModel.getCategoryList()
            val recyclerView = binding.recyclerViewCategories
            recyclerView.adapter = CategoryListAdapter(categoryList)
            binding.progressBar.visibility = View.GONE
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}