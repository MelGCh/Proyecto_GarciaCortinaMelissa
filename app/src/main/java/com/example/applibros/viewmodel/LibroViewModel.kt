package com.example.applibros.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applibros.model.Libro
import com.example.applibros.repositories.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibroViewModel(private val repository: LibroRepository) : ViewModel() {

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    fun cargarLibros() {
        viewModelScope.launch {
            _libros.value = repository.getAllLibros()
        }
    }

    fun guardarLibro(libro: Libro) {
        viewModelScope.launch {
            repository.addLibro(libro)
            cargarLibros()
        }
    }

    fun eliminarLibro(libro: Libro) {
        viewModelScope.launch {
            repository.deleteLibro(libro)
            cargarLibros()
        }
    }

    fun cambiarFavorito(libro: Libro) {
        val libroActualizado = libro.copy(favorito = !libro.favorito)

        viewModelScope.launch {
            repository.updateLibro(libroActualizado)
            cargarLibros()
        }
    }
}