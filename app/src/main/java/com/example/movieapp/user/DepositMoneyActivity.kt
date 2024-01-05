package com.example.movieapp.user

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.viewmodel.AppDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepositMoneyActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_money)
        val btnDepositMoney = findViewById<Button>(R.id.btnDepositMoney)
        val edtDepositMoney = findViewById<EditText>(R.id.tvDepositMoney)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val appDAO: AppDAO = db.appDao()
        val userName = intent.getStringExtra("userName")
        btnDepositMoney.setOnClickListener {
            val depositMoney = edtDepositMoney.text.toString()
                if (depositMoney.isNullOrEmpty()) {
                    //Mời bạn nhập số tiền
                    Toast.makeText(this,"Please enter the amount" ,Toast.LENGTH_SHORT).show()
                } else {
                    if (depositMoney.toDouble() < 50000.0) {
                        //Số tiền nạp phải trên 50000đ
                        Toast.makeText(this, "The deposit amount must be over 50,000 VND", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val user = userName?.let { it1 -> appDAO.getUser(it1) }
                            var updateMoney = user?.money?.plus(depositMoney.toDouble())
                            if (updateMoney != null) {
                                appDAO.updateMoneyUser(updateMoney.toDouble(), userName)
                            }
                        }
                        //Nạp Tiền Thành Công
                        Toast.makeText(this, "Deposit Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            val intentMainUser = Intent(this, MainUserActivity::class.java)
            intentMainUser.putExtra("userName", userName)
            startActivity(intentMainUser)
        }
    }
}