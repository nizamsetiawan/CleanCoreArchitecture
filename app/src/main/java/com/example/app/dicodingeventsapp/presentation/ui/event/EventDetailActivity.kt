package com.example.app.dicodingeventsapp.presentation.ui.event

import EventViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.app.dicodingeventsapp.R
import com.example.app.dicodingeventsapp.core.CoreActivity
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity
import com.example.app.dicodingeventsapp.data.remote.response.Event
import com.example.app.dicodingeventsapp.databinding.ActivityEventDetailBinding
import com.example.app.dicodingeventsapp.presentation.utils.ResponseState
import com.example.app.dicodingeventsapp.presentation.utils.dataFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailActivity : CoreActivity<ActivityEventDetailBinding>() {

    private val eventViewModel: EventViewModel by viewModel()
    private var isFavorite: Boolean = false
    private lateinit var eventId: String

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityEventDetailBinding {
        return ActivityEventDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = intent.getStringExtra("eventId") ?: return
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        eventViewModel.eventDetail.observe(this) { state ->
            when (state) {
                is ResponseState.Loading -> showLoading(true)
                is ResponseState.Success -> {
                    showLoading(false)
                    bindEventDetail(state.data)
                }
                is ResponseState.Error -> {
                    showLoading(false)
                    showError(state.errorMessage)
                }
            }
        }

        eventViewModel.isEventFavorite(eventId).observe(this) { favorite ->
            isFavorite = favorite
            updateFavoriteIcon()
        }

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                removeFavoriteEvent()
            } else {
                addFavoriteEvent()
            }
        }

        eventViewModel.getEventDetail(eventId)
    }

    private fun bindEventDetail(event: Event) {
        binding.eventName.text = event.name
        binding.eventOwner.text = event.ownerName
        binding.eventTime.text = dataFormatter(event.beginTime.toString())
        binding.eventQuota.text = "${event.quota?.minus(event.registrants!!)}"
        binding.eventDescription.text = HtmlCompat.fromHtml(
            event.description!!,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.eventLink.setOnClickListener {
            event.link?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        Glide.with(this)
            .load(event.imageLogo)
            .into(binding.eventImage)
    }

    private fun addFavoriteEvent() {
        val imageUrl = (eventViewModel.eventDetail.value as? ResponseState.Success)?.data?.imageLogo ?: ""
        val favoriteEvent = FavoriteEventEntity(id = eventId, name = binding.eventName.text.toString(), mediaCover = imageUrl)
        eventViewModel.addFavoriteEvent(favoriteEvent)
        isFavorite = true
        updateFavoriteIcon()
        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun removeFavoriteEvent() {
        eventViewModel.removeFavoriteEvent(eventId)
        isFavorite = false
        updateFavoriteIcon()
        Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteIcon() {
        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_active else R.drawable.ic_favorite_inactive)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.eventLink.isEnabled = !isLoading
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
