package com.example.guiapocket.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guiapocket.databinding.ItemServiceBinding
import com.example.guiapocket.model.Service

class ServiceAdapter(
    private val services: List<Service>,
    private val onClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.tvNome.text = service.nome
            binding.tvCategoria.text = service.categoria

            if (service.foto.isNotEmpty()) {
                binding.imgServico.setImageURI(Uri.parse(service.foto))
            }

            binding.root.setOnClickListener { onClick(service) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

    fun updateList(newServices: List<Service>) {
        (services as? MutableList)?.clear()
        (services as? MutableList)?.addAll(newServices)
        notifyDataSetChanged()
    }
}