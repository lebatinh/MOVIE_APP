package com.example.movieapp.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.activity.LoginActivity
import com.example.movieapp.data.Movie
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel

class AdminActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)
        val btnBack = findViewById<ImageButton>(R.id.btnBackAdminActivity)
        val btnUser = findViewById<Button>(R.id.btnUserManagement)
        val btnResetByDay = findViewById<Button>(R.id.btnResetDay)
        val btnResetByMont = findViewById<Button>(R.id.btnResetMonth)
        val btnRevenue = findViewById<Button>(R.id.btnRevenueManagement)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val appDAO: AppDAO = db.appDao()
        val mainViewModel = MainViewModel(appDAO)

        btnUser.setOnClickListener {
            val intentUser = Intent(this, MangementUserActivity::class.java)
            startActivity(intentUser)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnRevenue.setOnClickListener {
            val intentRevenua = Intent(this, RevenueActivity::class.java)
            startActivity(intentRevenua)
        }

        btnResetByDay.setOnClickListener {
            mainViewModel.resetEndDay()
            //Resat thành công
            Toast.makeText(this,"Resat successful",Toast.LENGTH_SHORT).show()
        }

        btnResetByMont.setOnClickListener {
            mainViewModel.resetEndMonth()
            //Resat thành công
            Toast.makeText(this,"Resat successful",Toast.LENGTH_SHORT).show()
        }


    }
}