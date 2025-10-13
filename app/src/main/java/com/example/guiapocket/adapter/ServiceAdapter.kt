package com.example.guiapocket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.guiapocket.databinding.ItemServiceBinding
import com.example.guiapocket.model.Service

class ServiceAdapter(
    context: Context,
    private val lista: List<Service>
) : ArrayAdapter<Service>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemServiceBinding
        val itemView: View

        if (convertView == null) {
            binding = ItemServiceBinding.inflate(LayoutInflater.from(context), parent, false)
            itemView = binding.root
            itemView.tag = binding
        } else {
            itemView = convertView
            binding = itemView.tag as ItemServiceBinding
        }

        val service = lista[position]
        binding.imgServico.setImageResource(service.foto)
        binding.tvNome.text = service.nome
        binding.tvCategoria.text = service.categoria

        return itemView
    }
}