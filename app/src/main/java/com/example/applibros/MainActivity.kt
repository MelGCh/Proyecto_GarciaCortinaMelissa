package com.example.applibros

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applibros.adapter.LibroAdapter
import com.example.applibros.database.AppDatabase
import com.example.applibros.databinding.ActivityMainBinding
import com.example.applibros.model.Libro
import com.example.applibros.repositories.LibroRepository
import com.example.applibros.viewmodel.LibroViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: LibroAdapter
    private lateinit var viewModel: LibroViewModel
    private lateinit var repository: LibroRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        repository = LibroRepository(db.libroDao())
        viewModel = LibroViewModel(repository)

        configurarSpinner()
        configurarRecyclerView()

        binding.btnGuardar.setOnClickListener {
            guardarLibro()
        }

        viewModel.cargarLibros()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.libros.collect { listaDeLibros ->
                    adapter.actualizarLista(listaDeLibros.toMutableList())
                }
            }
        }
    }

    private fun configurarSpinner() {
        val estados = listOf("Pendiente", "Leyendo", "Terminado")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnEstado.adapter = spinnerAdapter
    }

    private fun configurarRecyclerView() {
        adapter = LibroAdapter(
            mutableListOf(),
            onEliminarClick = { libro ->
                viewModel.eliminarLibro(libro)
                Toast.makeText(this, "Libro eliminado", Toast.LENGTH_SHORT).show()
            },
            onFavoritoClick = { libro ->
                viewModel.cambiarFavorito(libro)
                Toast.makeText(this, "Favorito actualizado", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvwLibros.layoutManager = LinearLayoutManager(this)
        binding.rvwLibros.adapter = adapter
    }

    private fun guardarLibro() {
        val titulo = binding.edtTitulo.text.toString().trim()
        val autor = binding.edtAutor.text.toString().trim()
        val genero = binding.edtGenero.text.toString().trim()
        val estado = binding.spnEstado.selectedItem.toString()
        val calificacionTexto = binding.edtCalificacion.text.toString().trim()
        val comentario = binding.edtComentario.text.toString().trim()
        val favorito = binding.chkFavorito.isChecked

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || calificacionTexto.isEmpty()) {
            Toast.makeText(this, "Llena los requisitos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val calificacion = calificacionTexto.toIntOrNull()

        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            Toast.makeText(this, "La calificación debe estar entre 1 y 5", Toast.LENGTH_SHORT).show()
            return
        }

        val libro = Libro(
            titulo = titulo,
            autor = autor,
            genero = genero,
            estado = estado,
            calificacion = calificacion,
            comentario = comentario,
            favorito = favorito
        )

        viewModel.guardarLibro(libro)

        Toast.makeText(this, "Libro guardado", Toast.LENGTH_SHORT).show()
        limpiarFormulario()
    }

    private fun limpiarFormulario() {
        binding.edtTitulo.text.clear()
        binding.edtAutor.text.clear()
        binding.edtGenero.text.clear()
        binding.edtCalificacion.text.clear()
        binding.edtComentario.text.clear()
        binding.chkFavorito.isChecked = false
        binding.spnEstado.setSelection(0)
    }
}