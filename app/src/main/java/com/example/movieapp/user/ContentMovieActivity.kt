package com.example.movieapp.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.viewmodel.AppDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_movie)
        val btnReservation = findViewById<Button>(R.id.btnReservation)
        val btnBackContentMovie = findViewById<ImageButton>(R.id.btnBackContentActivity)
        val imgMovie = findViewById<ImageView>(R.id.imgMovie)
        val tvDislayMovieNameContentActicity = findViewById<TextView>(R.id.tvDisplayContentMovieName)
        val tvDisplayContent = findViewById<TextView>(R.id.tvDisplayContent)
        val tvDisplaySowTime = findViewById<TextView>(R.id.tvDisplayShowTimeContentActivity)

        val movieName = intent.getStringExtra("movieName")
        val userName = intent.getStringExtra("userName")

        val db = Room.databaseBuilder(
            // đối tượng mà có thể sử dụng truy cập tài nguyên và chức năng hệ thống của ứng dụng của bạn
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val appDAO: AppDAO = db.appDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val movie = movieName?.let { appDAO.getMovie(it) }
            withContext(Dispatchers.Main) {
                if (movie != null) {
                    movie.img_2?.let { imgMovie.setImageResource(it) }
                    tvDisplayContent.text = movie.content
                    tvDislayMovieNameContentActicity.text = movie.movieName
                    tvDisplaySowTime.text = movie.showtime
                }
            }
        }



        btnReservation.setOnClickListener {
            val intentHomepage = Intent(this@ContentMovieActivity, BuyTiketActivity::class.java)
            intentHomepage.putExtra("movieName", movieName)
            intentHomepage.putExtra("userName", userName)
            startActivity(intentHomepage)
        }

        btnBackContentMovie.setOnClickListener {
            finish()
        }

    }
}