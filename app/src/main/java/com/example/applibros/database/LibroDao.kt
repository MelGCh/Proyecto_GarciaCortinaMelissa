package com.example.applibros.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.applibros.model.Libro

@Dao
interface LibroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLibro(libro: Libro)

    @Query("SELECT * FROM libros_table ORDER BY id DESC")
    suspend fun obtenerLibros(): List<Libro>

    @Update
    suspend fun actualizarLibro(libro: Libro)

    @Delete
    suspend fun eliminarLibro(libro: Libro)
}