package com.example.guiapocket.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.guiapocket.R
import com.example.guiapocket.data.database.AppDatabase
import com.example.guiapocket.databinding.ActivityCadastroBinding
import com.example.guiapocket.model.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var db: AppDatabase
    private var selectedImageUri: String = ""

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            selectedImageUri = uri.toString()
            binding.imgService.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSelectImage.setOnClickListener {
            galleryLauncher.launch(arrayOf("image/*"))
        }

        binding.btnSave.setOnClickListener {
            saveService()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveService() {
        val name = binding.edtName.text.toString().trim()
        val category = binding.edtCategory.text.toString().trim()
        val description = binding.edtDescription.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val website = binding.edtWebsite.text.toString().trim()
        val address = binding.edtAddress.text.toString().trim()

        if (name.isEmpty() || category.isEmpty() || description.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, getString(R.string.required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val imageUri = if (selectedImageUri.isEmpty()) {
            ""
        } else {
            selectedImageUri
        }

        val service = Service(
            foto = imageUri,
            nome = name,
            categoria = category,
            descricao = description,
            telefone = phone,
            website = website,
            endereco = address
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.serviceDao().insert(service)
            runOnUiThread {
                Toast.makeText(this@CadastroActivity, getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}