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
import com.example.movieapp.adapter.SeatSelecterFromUserAdapter
import com.example.movieapp.data.Seat
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel

class  SeatManagementFromUserActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_management_from_user)
        val btnBack = findViewById<ImageButton>(R.id.btnBackSeatSelectFromUser)
        val recyclerviewSeatSelecterFromUser = findViewById<RecyclerView>(R.id.rcvSeatSelectedFromUser)


        val db = Room.databaseBuilder(
            // đối tượng mà có thể sử dụng truy cập tài nguyên và chức năng hệ thống của ứng dụng của bạn
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val appDAO: AppDAO = db.appDao()
        val mainViewModel = MainViewModel(appDAO)
        val listSeatSelecterFromUser = mutableListOf<Seat>()
        val seatSelecterFromUserAdapter = SeatSelecterFromUserAdapter(listSeatSelecterFromUser)
        recyclerviewSeatSelecterFromUser.adapter = seatSelecterFromUserAdapter

        mainViewModel.getSeatListSelecterFromUser(intent.getStringExtra("User Name").toString())


        mainViewModel.seatListSelectedFromUser.observe(this){
            listSeatSelecterFromUser.clear()
            listSeatSelecterFromUser.addAll(it?: emptyList())
            seatSelecterFromUserAdapter.notifyDataSetChanged()
        }

        //btn Back
        btnBack.setOnClickListener {
            finish()
        }
    }
}