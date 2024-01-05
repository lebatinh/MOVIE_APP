package com.example.movieapp.user

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
import com.example.movieapp.activity.LoginActivity
import com.example.movieapp.adapter.CategoryAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel

class HomePageActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val btnMainUser = findViewById<ImageButton>(R.id.btnMain)
        val btnBack: ImageButton = findViewById(R.id.btnBackHomePage)
        val recyclerViewCategory: RecyclerView = findViewById(R.id.rcvCategory)
        val recyclerViewMovie: RecyclerView = findViewById(R.id.rcvMovie)
        val movieAdapter = MovieAdapter()
        var userName = intent.getStringExtra("userName").toString()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val appDAO: AppDAO = db.appDao()
        val mainViewModel = MainViewModel(appDAO)
        recyclerViewMovie.adapter = movieAdapter
        mainViewModel.getMovieList()

        mainViewModel.movieList.observe(this) {
            movieAdapter.submitList(it)
            movieAdapter.onItemClick = { Movie ->
                val intentHomepage = Intent(this@HomePageActivity, ContentMovieActivity::class.java)
                intentHomepage.putExtra("movieName", Movie.movieName)
                intentHomepage.putExtra("userName", userName)
                startActivity(intentHomepage)
            }
        }

        mainViewModel.getCategoryList()

        mainViewModel.categoryList.observe(this) {
            val categoryAdapter = CategoryAdapter(it)
            recyclerViewCategory.adapter = categoryAdapter
            if (it.isNullOrEmpty()) {
                mainViewModel.insertCategoryList()
            } else {
                categoryAdapter.onItemClickCategory = {
                    mainViewModel.faceListByCategory(it)
                }
            }
        }

        btnBack.setOnClickListener {
            val intentMainUser = Intent(this@HomePageActivity, LoginActivity::class.java)
            startActivity(intentMainUser)
        }

        btnMainUser.setOnClickListener {
            val intentMainUser = Intent(this@HomePageActivity, MainUserActivity::class.java)
            intentMainUser.putExtra("userName", userName)
            startActivity(intentMainUser)
        }

    }
}