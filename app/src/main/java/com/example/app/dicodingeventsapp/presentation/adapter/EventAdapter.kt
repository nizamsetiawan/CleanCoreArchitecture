package com.example.app.dicodingeventsapp.presentation.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.dicodingeventsapp.data.local.entity.FavoriteEventEntity
import com.example.app.dicodingeventsapp.data.remote.response.ListEventsItem
import com.example.app.dicodingeventsapp.databinding.ItemEventBinding
import com.example.app.dicodingeventsapp.presentation.ui.event.EventDetailActivity

class EventAdapter(private var eventList: List<ListEventsItem>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    fun updateData(newData: List<ListEventsItem>) {
        eventList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = eventList.size

    fun setFavoriteEvents(newEvents: List<FavoriteEventEntity>) {
        eventList = newEvents.map { entity ->
            Log.d("EventAdapter", "ID: ${entity.id}, Name: ${entity.name}, Image URL: ${entity.mediaCover}")
            ListEventsItem(id = entity.id.toInt(), name = entity.name, imageLogo = entity.mediaCover)
        }
        notifyDataSetChanged()
    }

    class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
            binding.eventName.text = event.name

            val imageUrl = event.imageLogo
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.eventImage)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, EventDetailActivity::class.java)
                intent.putExtra("eventId", event.id.toString())
                context.startActivity(intent)
            }
        }
    }
}
