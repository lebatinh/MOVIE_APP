package com.example.movieapp.user

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.adapter.SeatAdapter
import com.example.movieapp.data.Movie
import com.example.movieapp.data.Seat
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuyTiketActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_tiket)

        val recyclerviewSeat = findViewById<RecyclerView>(R.id.rcvSeat)
        val btnBuyTicket = findViewById<Button>(R.id.btbBuyTicket)
        val btnBack = findViewById<ImageButton>(R.id.btnBackBuyTicket)
        val tvSeats = findViewById<TextView>(R.id.tvSeats)
        val tvMoney = findViewById<TextView>(R.id.tvMoney)
        var userName = intent.getStringExtra("userName").toString()
        var movieName = intent.getStringExtra("movieName").toString()

        var listSeatCheck = mutableListOf<Seat>()
        val db1 = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database-name"
        ).build()
        val appDAO1: AppDAO = db1.appDao()
        val mainViewModel1 = MainViewModel(appDAO1)




        // get Seat dựa theo tên phim
        mainViewModel1.getSeatList(movieName)
        val listSeat: MutableList<Seat> = mutableListOf()
        val seatAdapter = SeatAdapter(listSeat)
        recyclerviewSeat.adapter = seatAdapter


        //click Seat
        seatAdapter.onItemClickSeat = { seat, posistion ->
            if (listSeat[posistion].checkSeat) {
                //ghế đã được mua
                Toast.makeText(this,"Chairs have been purchased",Toast.LENGTH_SHORT).show()
            } else {
                listSeat[posistion].isSeat = !listSeat[posistion].isSeat
                if (listSeat[posistion].isSeat) {
                    listSeatCheck.add(listSeat[posistion])
                } else {
                    listSeatCheck.remove(listSeat[posistion])
                }
                seatAdapter.notifyItemChanged(posistion)
                var count = listSeatCheck.count()
                tvSeats.text = count.toString()
                tvMoney.text = (count*50).toString() + ".000"
            }
        }


        // btn mua vé xem phim
        btnBuyTicket.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = appDAO1.getUser(userName)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        if (listSeatCheck.size*50000.0 <= user.money!!) {
                            if (listSeatCheck.isEmpty()) {
                                //bạn chưa chọn ghế
                                Toast.makeText(
                                    this@BuyTiketActivity,
                                    "You haven't chosen a chair yet",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                for (i in 0 until listSeatCheck.size) {
                                    listSeatCheck[i].checkSeat = true
                                }
                                mainViewModel1.UpdateListCheckSeat(userName, listSeatCheck, movieName)
                                mainViewModel1.updataMoneyUser(listSeatCheck.size * 50000.0, userName)
                                //Đặt vé thành công
                                Toast.makeText(
                                    this@BuyTiketActivity,
                                    "Ticket booking successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            listSeatCheck = mutableListOf()
                            tvSeats.text = "0"
                            tvMoney.text = "0.0"
                        } else {
                            //tài khoản không đủ thanh toán
                            Toast.makeText(this@BuyTiketActivity,"account is insufficient to pay",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        mainViewModel1.seatList.observe(this) {
            if (it.isNullOrEmpty()) {
                mainViewModel1.CurrentInsertSeatList(movieName)
            } else {
                listSeat.clear()
                listSeat.addAll(it)
                seatAdapter.notifyDataSetChanged()
                Log.d("List",listSeat.toString())
            }
        }
        btnBack.setOnClickListener {
            finish()
        }



    }
}