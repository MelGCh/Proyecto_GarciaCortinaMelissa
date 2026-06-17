package com.example.applibros.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros_table")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var titulo: String,
    var autor: String,
    var genero: String,
    var estado: String,
    var calificacion: Int,
    var comentario: String,
    var favorito: Boolean
)