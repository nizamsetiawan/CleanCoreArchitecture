package com.example.app.dicodingeventsapp.presentation.ui.bookmarks

import EventViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.dicodingeventsapp.R
import com.example.app.dicodingeventsapp.core.CoreFragment
import com.example.app.dicodingeventsapp.databinding.FragmentBookmarksBinding
import com.example.app.dicodingeventsapp.presentation.adapter.EventAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksFragment : CoreFragment<FragmentBookmarksBinding>() {

    private val eventViewModel: EventViewModel by viewModel()
    private lateinit var adapter: EventAdapter

    override fun setupFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentBookmarksBinding {
        return FragmentBookmarksBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventAdapter(emptyList())
        binding.rvBookmarksEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookmarksEvents.adapter = adapter
        observeFavoriteEvents()
    }

    private fun observeFavoriteEvents() {
        eventViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { events ->
            if (events.isEmpty()) {
                isEmptyLayoutEnable(true)
            } else {
                isEmptyLayoutEnable(false)
                adapter.setFavoriteEvents(events)
            }
        }
    }

    private fun isEmptyLayoutEnable(isEnable: Boolean) {
        binding.apply {
            emptyLayoutFavouriteEvents.apply {
                if (isEnable) {
                    root.visibility = View.VISIBLE
                    tvEmptyTitle.text = getString(R.string.is_empty)
                    rvBookmarksEvents.visibility = View.GONE
                } else {
                    root.visibility = View.GONE
                    rvBookmarksEvents.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}
