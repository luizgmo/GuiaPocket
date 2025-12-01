package com.example.guiapocket.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "service")
data class Service(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foto: String,
    val nome: String,
    val categoria: String,
    val descricao: String,
    val telefone: String,
    val website: String,
    val endereco: String
) : Serializable