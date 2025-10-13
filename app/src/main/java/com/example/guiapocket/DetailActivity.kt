package com.example.guiapocket

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guiapocket.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupServiceDetails()
        setupClickListeners()
    }

    private fun setupServiceDetails() {
        binding.detailNameTextView.text = intent.getStringExtra("SERVICE_NAME")
        binding.detailCategoryTextView.text = intent.getStringExtra("SERVICE_CATEGORY")
        binding.detailDescriptionTextView.text = intent.getStringExtra("SERVICE_DESCRIPTION")

        val imageResId = intent.getIntExtra("SERVICE_IMAGE", R.drawable.ic_default)
        binding.detailImageView.setImageResource(imageResId)
    }

    private fun setupClickListeners() {
        binding.callButton.setOnClickListener {
            val phoneNumber = intent.getStringExtra("SERVICE_PHONE")
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }

        binding.websiteButton.setOnClickListener {
            val website = intent.getStringExtra("SERVICE_WEBSITE")
            if (website?.isNotEmpty() == true) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(website)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.no_website), Toast.LENGTH_SHORT).show()
            }
        }

        binding.mapsButton.setOnClickListener {
            val address = intent.getStringExtra("SERVICE_ADDRESS")
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
            }
            startActivity(intent)
        }

        binding.shareButton.setOnClickListener {
            val serviceName = intent.getStringExtra("SERVICE_NAME") ?: ""
            val serviceDescription = intent.getStringExtra("SERVICE_DESCRIPTION") ?: ""
            val serviceAddress = intent.getStringExtra("SERVICE_ADDRESS") ?: ""
            val servicePhone = intent.getStringExtra("SERVICE_PHONE") ?: ""

            val shareText = """
            $serviceName
            $serviceDescription
            
            📍 $serviceAddress
            📞 $servicePhone
            
            Recomendado pelo Guia Pocket - Jardim Universal
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, serviceName)
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
        }
    }
}