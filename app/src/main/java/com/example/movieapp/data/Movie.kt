package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "tb_movie")
data class Movie(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val movieName: String?,
    var img: Int?,
    var img_2: Int?,
    var content: String?,
    val nowPlay: Boolean?,
    val favourite: Boolean?,
    val vietNamMovie: Boolean?,
    val popular: Boolean?,
    val revenue: Double? = 0.0,
    val showtime: String?,
)