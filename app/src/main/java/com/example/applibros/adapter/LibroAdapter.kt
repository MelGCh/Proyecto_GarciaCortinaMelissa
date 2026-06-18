package com.example.applibros.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applibros.databinding.ItemLibroBinding
import com.example.applibros.model.Libro

class LibroAdapter(
    private var libros: MutableList<Libro>,
    private val onEliminarClick: (Libro) -> Unit,
    private val onFavoritoClick: (Libro) -> Unit
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    class LibroViewHolder(val binding: ItemLibroBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLibroBinding.inflate(inflater, parent, false)
        return LibroViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]

        holder.binding.tvwTituloLibro.text = libro.titulo
        holder.binding.tvwAutorLibro.text = "Autor: ${libro.autor}"
        holder.binding.tvwGeneroLibro.text = "Género: ${libro.genero}"
        holder.binding.tvwEstadoLibro.text = "Estado: ${libro.estado}"
        holder.binding.tvwCalificacionLibro.text = "Calificación: ${libro.calificacion}/5"

        holder.binding.tvwComentarioLibro.text =
            if (libro.comentario.isBlank()) {
                "Comentario: Sin comentario"
            } else {
                "Comentario: ${libro.comentario}"
            }

        holder.binding.tvwComentarioLibro.text = if (libro.comentario.isBlank())
            "Comentario: Sin comentario" else "Comentario: ${libro.comentario}"

        holder.binding.tvwFavoritoLibro.text = if (libro.favorito)
            "Favorito: Sí" else "Favorito: No"

        holder.binding.btnFavorito.text = if (libro.favorito)
            "Quitar favorito" else "Favorito"
        holder.binding.ivFavoritoIcono.visibility =
            if (libro.favorito) android.view.View.VISIBLE else android.view.View.GONE

        holder.binding.btnEliminar.setOnClickListener {
            onEliminarClick(libro)
        }

        holder.binding.btnFavorito.setOnClickListener {
            onFavoritoClick(libro)
        }
        holder.binding.tvwTituloLibro.text = libro.titulo
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    fun actualizarLista(nuevaLista: MutableList<Libro>) {
        libros = nuevaLista
        notifyDataSetChanged()
    }
}