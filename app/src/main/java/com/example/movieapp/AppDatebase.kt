package com.example.movieapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.Category
import com.example.movieapp.data.Movie
import com.example.movieapp.data.Seat
import com.example.movieapp.data.User
import com.example.movieapp.viewmodel.AppDAO

@Database(entities = [User::class, Movie::class, Category::class, Seat::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao():AppDAO
}