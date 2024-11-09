package com.example.app.dicodingeventsapp.presentation.ui.home

import EventViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.dicodingeventsapp.core.CoreFragment
import com.example.app.dicodingeventsapp.databinding.FragmentHomeBinding
import com.example.app.dicodingeventsapp.presentation.adapter.EventAdapter
import com.example.app.dicodingeventsapp.presentation.ui.event.SearchActivity
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import org.koin.androidx.viewmodel.ext.android.viewModel
class HomeFragment : CoreFragment<FragmentHomeBinding>() {

    private val eventViewModel: EventViewModel by viewModel()
    private lateinit var upcomingAdapter: EventAdapter
    private lateinit var finishedAdapter: EventAdapter
    private var isLoadingUpcoming = false
    private var isLoadingFinished = false

    override fun setupFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        binding.fabSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        eventViewModel.upcomingEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    isLoadingUpcoming = true
                    updateLoadingState()
                }
                is ResponseState.Success -> {
                    isLoadingUpcoming = false
                    upcomingAdapter.updateData(state.data)
                    updateLoadingState()
                }
                is ResponseState.Error -> {
                    isLoadingUpcoming = false
                    showError(state.errorMessage)
                    updateLoadingState()
                }
            }
        }

        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    isLoadingFinished = true
                    updateLoadingState()
                }
                is ResponseState.Success -> {
                    isLoadingFinished = false
                    finishedAdapter.updateData(state.data)
                    updateLoadingState()
                }
                is ResponseState.Error -> {
                    isLoadingFinished = false
                    showError(state.errorMessage)
                    updateLoadingState()
                }
            }
        }

        eventViewModel.getUpcomingEvents()
        eventViewModel.getFinishedEvents()
    }

    private fun setupRecyclerViews() {
        upcomingAdapter = EventAdapter(emptyList())
        finishedAdapter = EventAdapter(emptyList())
        binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFinishedEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcomingEvents.adapter = upcomingAdapter
        binding.rvFinishedEvents.adapter = finishedAdapter
    }

    private fun updateLoadingState() {
        binding.progressBar.visibility = if (isLoadingUpcoming || isLoadingFinished) View.VISIBLE else View.GONE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}

