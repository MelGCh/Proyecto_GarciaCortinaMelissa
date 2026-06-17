package com.example.applibros.repositories

import com.example.applibros.database.LibroDao
import com.example.applibros.model.Libro

class LibroRepository(private val libroDao: LibroDao) {

    suspend fun addLibro(libro: Libro) = libroDao.insertarLibro(libro)

    suspend fun getAllLibros() = libroDao.obtenerLibros()

    suspend fun updateLibro(libro: Libro) = libroDao.actualizarLibro(libro)

    suspend fun deleteLibro(libro: Libro) = libroDao.eliminarLibro(libro)
}