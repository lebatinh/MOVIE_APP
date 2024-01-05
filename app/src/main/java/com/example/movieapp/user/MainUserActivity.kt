package com.example.movieapp.user

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.adapter.SeatSelectMainUserAdapter
import com.example.movieapp.data.Seat
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainUserActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)
        val btnBack = findViewById<ImageButton>(R.id.btnBackMainUser)
        val rcvSeatMainUser = findViewById<RecyclerView>(R.id.rcvSeatMainUser)
        val tvFistName = findViewById<TextView>(R.id.tvFistName)
        val tvDisplayTotalSpending = findViewById<TextView>(R.id.tvDisplayTotalSpending)
        val tvDisplayLever = findViewById<TextView>(R.id.tvDisplayLevel)
        val tvNoData = findViewById<TextView>(R.id.tvNoData)
        val tvNickName = findViewById<TextView>(R.id.tvDisplayNickNameMainUser)
        val tvDisplayMoney = findViewById<TextView>(R.id.tvDisplayMoney)
        val btnMoreMoney = findViewById<ImageButton>(R.id.btnMoreMoney)
        val userName  = intent.getStringExtra("userName").toString()



        Log.d("userName",userName)
        val db = Room.databaseBuilder(
            // đối tượng mà có thể sử dụng truy cập tài nguyên và chức năng hệ thống của ứng dụng của bạn
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val appDAO: AppDAO = db.appDao()

        // lấy đối tượng user theo  userName show một vài thuộc tính của user ra màn hình
        lifecycleScope.launch(Dispatchers.IO) {
            val user = appDAO.getUser(userName)
           withContext(Dispatchers.Main) {
               if (user != null) {

                   tvFistName.text = user.emailName?.first().toString()
                   tvDisplayTotalSpending.text = user.totalSpending.toString()+ "đ"
                   tvNickName.text = user.nickname.toString()
                   tvDisplayMoney.text = user.money.toString() + "đ"
               } else {
                   tvDisplayMoney.text = "0đ"
                   tvDisplayTotalSpending.text = "0đ"
               }
           }
        }


        val mainViewModel = MainViewModel(appDAO)
        val listSeatFromMainUser = mutableListOf<Seat>()
        val seatMainUserAdapter = SeatSelectMainUserAdapter(listSeatFromMainUser)
        rcvSeatMainUser.adapter = seatMainUserAdapter
        mainViewModel.getSeatListSelecterFromUser(userName)




        mainViewModel.seatListSelectedFromUser.observe(this){
            listSeatFromMainUser.clear()
            if (it.isNullOrEmpty()) {
                tvNoData.text = "No Data"

            } else {
                tvNoData.text = ""
                listSeatFromMainUser.addAll(it)
            }
            Log.d("it",it.toString())
            seatMainUserAdapter.notifyDataSetChanged()
        }

        seatMainUserAdapter.onClickDeleteTicket = { seat ->
            lifecycleScope.launch(Dispatchers.IO) {
                seat.movieName?.let {
                    mainViewModel.deleteSeat(it, seat.seatNumber, userName, seat)
                }
                mainViewModel.updataMoneyUser(-50000.0, userName)
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val user = appDAO.getUser(userName)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        tvFistName.text = user.emailName?.first().toString()
                        tvDisplayTotalSpending.text = user.totalSpending.toString() + "đ"
                        tvNickName.text = user.nickname.toString()
                        tvDisplayMoney.text = user.money.toString() + "đ"
                        if (user != null) {
                            Log.d("Tien", user.money.toString())
                        }
                    } else {
                        tvDisplayMoney.text = "0đ"
                        tvDisplayTotalSpending.text = "0đ"
                    }
                }
            }
            val intent = Intent(this, this::class.java)
            intent.putExtra("userName",userName)
            startActivity(intent)
            finish()
        }

        btnBack.setOnClickListener {
            val intentBack = Intent(this, HomePageActivity::class.java)
            intentBack.putExtra("userName",userName)
            startActivity(intentBack)
        }

        btnMoreMoney.setOnClickListener {
            val intentMainUser = Intent(this, DepositMoneyActivity::class.java)
            intentMainUser.putExtra("userName", userName)
            startActivity(intentMainUser)
        }
    }
}