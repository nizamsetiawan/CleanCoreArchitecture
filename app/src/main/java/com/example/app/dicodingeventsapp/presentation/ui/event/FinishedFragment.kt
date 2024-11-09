package com.example.app.dicodingeventsapp.presentation.ui.event

import EventViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.dicodingeventsapp.core.CoreFragment
import com.example.app.dicodingeventsapp.databinding.FragmentFinishedBinding
import com.example.app.dicodingeventsapp.presentation.adapter.EventAdapter
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import org.koin.androidx.viewmodel.ext.android.viewModel
class FinishedFragment : CoreFragment<FragmentFinishedBinding>() {

    private val eventViewModel: EventViewModel by viewModel()
    private lateinit var adapter: EventAdapter

    override fun setupFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentFinishedBinding {
        return FragmentFinishedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventAdapter(emptyList())
        binding.rvFinishedEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEvents.adapter = adapter

        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> showLoading(true)
                is ResponseState.Success -> {
                    adapter.updateData(state.data)
//                    Toast.makeText(requireContext(), "Finished events loaded", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
                is ResponseState.Error -> showError(state.errorMessage)
            }
        }

        eventViewModel.getFinishedEvents()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}

