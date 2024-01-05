package com.example.movieapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.admin.AdminActivity
import com.example.movieapp.user.HomePageActivity
import com.example.movieapp.viewmodel.AppDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val edtEmailLogin = findViewById<EditText>(R.id.edtEmailLogin)
        val edtPasswordlLogin = findViewById<EditText>(R.id.edtPasswordLogin)
        val btnCreateLogin = findViewById<Button>(R.id.btnCreateLogin)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val radioGroupCheckUsear = findViewById<RadioGroup>(R.id.rdgRoll)
        val tvRessetPassword = findViewById<TextView>(R.id.tvResetPasswordInLogin)
        var checkUser: Int = 0
        val db = Room.databaseBuilder(
            // đối tượng mà có thể sử dụng chuy cập tài nguyên và chức năng hệ thống của ứng dụng của bạn
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val appDAO: AppDAO = db.appDao()
        val sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        edtEmailLogin.setText(sharedPreferences.getString("emailName",""))
        edtPasswordlLogin.setText(sharedPreferences.getString("password",""))
        radioGroupCheckUsear.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rdb_admin -> {
                    checkUser = 1
                }
                R.id.rdb_user -> {
                    checkUser = 2
                }
            }
        }
        btnCreateLogin.setOnClickListener {
            val emailLogin = edtEmailLogin.text.toString()
            val passwordlLogin = edtPasswordlLogin.text.toString()
            if(checkUser == 2) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userEmail = appDAO.login(emailLogin, passwordlLogin)
                    if (emailLogin.isEmpty()  && passwordlLogin.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            //bạn nhập thiếu thông tin
                            Toast.makeText(
                                this@LoginActivity,
                                "You entered missing information",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        if (userEmail == true) {
                            val intentLogin = Intent(this@LoginActivity, HomePageActivity::class.java)
                            intentLogin.putExtra("userName",emailLogin)
                            startActivity(intentLogin)
                        } else
                        {
                            withContext(Dispatchers.Main) {
                                //đăng nhập thất bại
                                Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        }
                    }
                }
            } else {
                if (checkUser == 1) {
                    if (emailLogin.trim().equals("admin") && passwordlLogin.trim().equals("1234")) {
                    val intentAdimn = Intent(this@LoginActivity, AdminActivity::class.java)
                    startActivity(intentAdimn)
                } else {
                    //Đăng Nhập thất bại
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT)
                            .show()
                }
                }
            }
            if (checkUser == 0) {
                //bạn chưa chọn cách thức đăng nhập
                Toast.makeText(this@LoginActivity, "\n" + "You have not chosen a login method", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        tvLogin.setOnClickListener {
            val intentLoginActivityNextRegisterActivity = Intent(edtPasswordlLogin.context, RegisterActivity::class.java)
            startActivity(intentLoginActivityNextRegisterActivity)
        }

        tvRessetPassword.setOnClickListener {
            val intentLoginActivityNextResetPasswordActivty = Intent(edtPasswordlLogin.context, ResetPasswordActivity::class.java)
            startActivity(intentLoginActivityNextResetPasswordActivty)
        }
    }
}