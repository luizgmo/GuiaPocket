package com.example.guiapocket.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guiapocket.adapter.ServiceAdapter
import com.example.guiapocket.data.database.AppDatabase
import com.example.guiapocket.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ServiceAdapter
    private lateinit var db: AppDatabase
    private var searchJob: Job? = null
    private var allServices = mutableListOf<com.example.guiapocket.model.Service>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        setupRecyclerView()
        setupListeners()
        loadServices()
    }

    private fun setupRecyclerView() {
        adapter = ServiceAdapter { service ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("SERVICE_ID", service.id)
            }
            startActivity(intent)
        }

        binding.recyclerViewServices.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupListeners() {
        binding.btnAddService.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        binding.edtFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filter = s.toString().trim()
                performSearch(filter)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performSearch(filter: String) {
        searchJob?.cancel()

        searchJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val filteredList = if (filter.isEmpty()) {
                    allServices
                } else {
                    allServices.filter { service ->
                        service.nome.contains(filter, ignoreCase = true) ||
                                service.categoria.contains(filter, ignoreCase = true)
                    }
                }

                withContext(Dispatchers.Main) {
                    adapter.updateList(filteredList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    adapter.updateList(allServices)
                }
            }
        }
    }

    private fun loadServices() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val services = db.serviceDao().getAll()
                withContext(Dispatchers.Main) {
                    allServices.clear()
                    allServices.addAll(services)
                    adapter.updateList(allServices)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadServices()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
    }
}