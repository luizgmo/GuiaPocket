package com.example.guiapocket.data.dao

import androidx.room.*
import com.example.guiapocket.model.Service

@Dao
interface ServiceDao {

    @Insert
    suspend fun insert(service: Service)

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)

    @Query("SELECT * FROM service ORDER BY nome ASC")
    suspend fun getAll(): List<Service>

    @Query("SELECT * FROM service WHERE LOWER(nome) LIKE '%' || LOWER(:filter) || '%' OR LOWER(categoria) LIKE '%' || LOWER(:filter) || '%' ORDER BY nome ASC")
    suspend fun filterByName(filter: String): List<Service>

    @Query("SELECT * FROM service WHERE id = :id")
    suspend fun getById(id: Int): Service?
}