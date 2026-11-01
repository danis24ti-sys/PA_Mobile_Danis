package com.example.projeklaundry_danis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeklaundry_danis.databinding.ItemServiceBinding
import java.util.Locale

data class LaundryService(
    val name: String,
    val description: String,
    val price: String
)

class ServicesAdapter(
    private val allServices: List<LaundryService>
) : RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {
    private var visibleServices = allServices.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(visibleServices[position])
    }

    override fun getItemCount(): Int = visibleServices.size

    fun filterServices(query: String) {
        val keyword = query.trim().lowercase(Locale.ROOT)
        visibleServices = if (keyword.isEmpty()) {
            allServices.toList()
        } else {
            allServices.filter { it.name.lowercase(Locale.ROOT).contains(keyword) }
        }
        notifyDataSetChanged()
    }

    class ServiceViewHolder(
        private val binding: ItemServiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(service: LaundryService) {
            binding.serviceName.text = service.name
            binding.serviceDescription.text = service.description
            binding.servicePrice.text = service.price
        }
    }
}
