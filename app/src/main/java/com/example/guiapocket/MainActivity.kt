package com.example.guiapocket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.guiapocket.adapter.ServiceAdapter
import com.example.guiapocket.databinding.ActivityMainBinding
import com.example.guiapocket.model.Service

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var services: List<Service>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        services = listOf(
            Service(
                id = 1,
                foto = R.drawable.ic_bakery,
                nome = getString(R.string.padaria_nsa),
                categoria = getString(R.string.bakery),
                descricao = getString(R.string.padaria_nsa_description),
                telefone = "+5516997465878",
                website = "https://www.instagram.com/padaria.nsa/",
                endereco = getString(R.string.padaria_nsa_address)
            ),
            Service(
                id = 2,
                foto = R.drawable.ic_transport,
                nome = getString(R.string.enricar_frotas),
                categoria = getString(R.string.transport),
                descricao = getString(R.string.enricar_frotas_description),
                telefone = "+551633315981",
                website = "https://www.instagram.com/enricarfrotas/",
                endereco = getString(R.string.enricar_frotas_address)
            ),
            Service(
                id = 3,
                foto = R.drawable.ic_lab,
                nome = getString(R.string.centerlab_ambiental),
                categoria = getString(R.string.laboratory),
                descricao = getString(R.string.centerlab_ambiental_description),
                telefone = "+551633315400",
                website = "https://www.centerlabambiental.com/",
                endereco = getString(R.string.centerlab_ambiental_address)
            ),
            Service(
                id = 4,
                foto = R.drawable.ic_auto_parts,
                nome = getString(R.string.american_rolamentos),
                categoria = getString(R.string.auto_parts),
                descricao = getString(R.string.american_rolamentos_description),
                telefone = "+551633352537",
                website = "https://www.americanrolamentos.com.br/",
                endereco = getString(R.string.american_rolamentos_address)
            ),
            Service(
                id = 5,
                foto = R.drawable.ic_mechanic,
                nome = getString(R.string.viccar_diesel),
                categoria = getString(R.string.mechanic),
                descricao = getString(R.string.viccar_diesel_description),
                telefone = "+551633221935",
                website = "https://diskfone.com.br/cliente/L5lA",
                endereco = getString(R.string.viccar_diesel_address)
            ),
            Service(
                id = 6,
                foto = R.drawable.ic_travel,
                nome = getString(R.string.vpm_viagens),
                categoria = getString(R.string.travel_agency),
                descricao = getString(R.string.vpm_viagens_description),
                telefone = "+5516992464388",
                website = "https://vpmviagens.com.br/",
                endereco = getString(R.string.vpm_viagens_address)
            )
        ).sortedBy { it.nome }
    }

    private fun setupViews() {
        val adapter = ServiceAdapter(this, services)
        binding.listViewServicos.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewServicos.setOnItemClickListener { _, _, position, _ ->
            val service = services[position]

            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("SERVICE_ID", service.id)
                putExtra("SERVICE_NAME", service.nome)
                putExtra("SERVICE_CATEGORY", service.categoria)
                putExtra("SERVICE_DESCRIPTION", service.descricao)
                putExtra("SERVICE_PHONE", service.telefone)
                putExtra("SERVICE_WEBSITE", service.website)
                putExtra("SERVICE_ADDRESS", service.endereco)
                putExtra("SERVICE_IMAGE", service.foto)
            }
            startActivity(intent)
        }
    }
}