package com.example.movieapp.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.adapter.UserAdapter
import com.example.movieapp.data.User
import com.example.movieapp.viewmodel.AppDAO
import com.example.movieapp.viewmodel.MainViewModel

class MangementUserActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mangement_user)
        val recyclerviewUser = findViewById<RecyclerView>(R.id.rcvUser)
        val btnBack = findViewById<ImageButton>(R.id.btnBackUserManagement)
        val tvNoUserData = findViewById<TextView>(R.id.tvNoUserData)

        val db = Room.databaseBuilder(
            // đối tượng mà có thể sử dụng truy cập tài nguyên và chức năng hệ thống của ứng dụng của bạn
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val appDAO: AppDAO = db.appDao()
        val mainViewModel = MainViewModel(appDAO)
        mainViewModel.getUserList()
        val listUser = mutableListOf<User>()
        val userAdapter = UserAdapter(listUser)
        recyclerviewUser.adapter = userAdapter

        userAdapter.onClickDeleteUser = { user ->
            if (user.money!! > 1.0) {
                //Không thể xóa tài khoản
                Toast.makeText(this, "Cannot delete User account", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.deleteUser(user)
                userAdapter.notifyDataSetChanged()
            }
        }

        mainViewModel.UserList.observe(this) {
            listUser.clear()
            listUser.addAll(it ?: emptyList())
            userAdapter.notifyDataSetChanged()
            if (it.isNullOrEmpty()){
                tvNoUserData.text = "No data"
            } else {
                tvNoUserData.text = ""
            }
        }

        userAdapter.onItemClickUser = {
            val intentFromUserName =
                Intent(this@MangementUserActivity, SeatManagementFromUserActivity::class.java)
            intentFromUserName.putExtra("User Name", it.emailName)
            startActivity(intentFromUserName)
        }

        btnBack.setOnClickListener {
            finish()
        }

    }
}