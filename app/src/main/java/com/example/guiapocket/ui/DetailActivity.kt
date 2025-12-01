package com.example.guiapocket.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.guiapocket.R
import com.example.guiapocket.data.database.AppDatabase
import com.example.guiapocket.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDatabase
    private var serviceId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        serviceId = intent.getIntExtra("SERVICE_ID", -1)

        if (serviceId == -1) {
            Toast.makeText(this, getString(R.string.error_loading_service), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadServiceDetails()
        setupClickListeners()
    }

    private fun loadServiceDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = db.serviceDao().getById(serviceId)
            withContext(Dispatchers.Main) {
                if (service != null) {
                    setupServiceDetails(service)
                } else {
                    Toast.makeText(this@DetailActivity, getString(R.string.service_not_found), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun setupServiceDetails(service: com.example.guiapocket.model.Service) {
        if (service.foto.isNotEmpty()) {
            binding.detailImageView.setImageURI(Uri.parse(service.foto))
        }

        binding.detailNameTextView.text = service.nome
        binding.detailCategoryTextView.text = service.categoria
        binding.detailDescriptionTextView.text = service.descricao

        binding.root.tag = service
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.btnDayNight.setOnClickListener {
            val isNight = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val mode = if (isNight) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        binding.btnIdioma.setOnClickListener {
            val idiomaAtual = AppCompatDelegate.getApplicationLocales()
            val usandoIdiomaPadrao = !idiomaAtual.isEmpty && idiomaAtual[0]?.language == "en"
            val proximoIdioma = if (usandoIdiomaPadrao) "pt" else "en"
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(proximoIdioma))
        }

        binding.callButton.setOnClickListener {
            val service = binding.root.tag as? com.example.guiapocket.model.Service
            service?.let {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${it.telefone}")
                }
                startActivity(intent)
            }
        }

        binding.websiteButton.setOnClickListener {
            val service = binding.root.tag as? com.example.guiapocket.model.Service
            service?.let {
                if (it.website.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(it.website)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.no_website), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.mapsButton.setOnClickListener {
            val service = binding.root.tag as? com.example.guiapocket.model.Service
            service?.let {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("geo:0,0?q=${Uri.encode(it.endereco)}")
                }
                startActivity(intent)
            }
        }

        binding.shareButton.setOnClickListener {
            val service = binding.root.tag as? com.example.guiapocket.model.Service
            service?.let {
                val shareText = """
                    ${it.nome}
                    ${it.descricao}
                    
                    📍 ${it.endereco}
                    📞 ${it.telefone}
                    
                    Recomendado pelo Guia Pocket - Jardim Universal
                """.trimIndent()

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, it.nome)
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
            }
        }
    }
}