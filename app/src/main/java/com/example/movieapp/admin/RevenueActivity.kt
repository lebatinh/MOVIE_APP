package com.example.movieapp.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.adapter.MovieRevenueAdapter
import com.example.movieapp.data.Movie
import com.example.movieapp.user.BuyTiketActivity
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel

class RevenueActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revenue)
        val recyclerViewMovieRevenue: RecyclerView = findViewById(R.id.rcvMovieRevenue)
        val btnBack: ImageButton = findViewById(R.id.btnBackRevenue)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val appDAO: AppDAO = db.appDao()
        val mainViewModel = MainViewModel(appDAO)
        val listMovieRevenue = mutableListOf<Movie>()
        val movieRevenueAdapter = MovieRevenueAdapter(listMovieRevenue)
        recyclerViewMovieRevenue.adapter = movieRevenueAdapter
        mainViewModel.getMovieList()

        mainViewModel.movieList.observe(this){
            listMovieRevenue.clear()
            listMovieRevenue.addAll(it?: emptyList())
            movieRevenueAdapter.notifyDataSetChanged()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}