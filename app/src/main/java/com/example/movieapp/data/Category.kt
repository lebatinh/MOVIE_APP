package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tb_category")
data class Category(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val categoryMovie: String?
)