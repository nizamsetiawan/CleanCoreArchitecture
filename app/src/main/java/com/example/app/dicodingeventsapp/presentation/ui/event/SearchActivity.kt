package com.example.app.dicodingeventsapp.presentation.ui.event

import EventViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.dicodingeventsapp.core.CoreActivity
import com.example.app.dicodingeventsapp.databinding.ActivitySearchBinding
import com.example.app.dicodingeventsapp.presentation.adapter.EventAdapter
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : CoreActivity<ActivitySearchBinding>() {

    private val eventViewModel: EventViewModel by viewModel()
    private lateinit var adapter: EventAdapter

    override fun setupBinding(layoutInflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = EventAdapter(emptyList())
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    eventViewModel.searchEvents(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        eventViewModel.searchResults.observe(this) { state ->
            when (state) {
                is ResponseState.Loading -> showLoading(true)
                is ResponseState.Success -> {
                    if (state.data.isEmpty()) {
                        Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.updateData(state.data)
                    }
                    showLoading(false)
                }
                is ResponseState.Error -> {
                    showError(state.errorMessage)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
