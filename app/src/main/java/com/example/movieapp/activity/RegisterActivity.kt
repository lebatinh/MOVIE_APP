package com.example.movieapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.data.User
import com.example.movieapp.viewmodel.AppDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val edtNickName = findViewById<EditText>(R.id.edtNickName)
        val edtPhoneNumber = findViewById<EditText>(R.id.edtPhoneNumberRegister)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordRegister)
        val edtConfirmPassword = findViewById<EditText>(R.id.edtConfirmPasswordRegister)
        val btnCreateAnAccount = findViewById<Button>(R.id.btnCreateRegister)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        lifecycleScope.launch(Dispatchers.IO) {
            val appDAO: AppDAO = db.appDao()
            btnCreateAnAccount.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val nickname = edtNickName.text.toString()
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()
                    val phoneNumber = edtPhoneNumber.text.toString()
                    val confirmPassword = edtConfirmPassword.text.toString()

                    if (email.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty() && phoneNumber.isNotEmpty() && confirmPassword.isNotEmpty()) {
                        if (isValidEmail(email)) {
                            val itemCount = appDAO.checkEmail(email)
                            if (itemCount == false) {
                                if (password.length >= 4) { // Check if password has at least 4 characters

                                    if (password == confirmPassword) {
                                        if (isValidPhoneNumber(phoneNumber)) {

                                            appDAO.insertUser(
                                                user = User(
                                                    emailName = email,
                                                    password = password,
                                                    phoneNumber = phoneNumber,
                                                    nickname = nickname,
                                                    money = 0.0
                                                )
                                            )
                                            withContext(Dispatchers.Main) {
                                                //Đăng kí thành công
                                                val toast = Toast.makeText(
                                                    applicationContext,
                                                    "Registered successfully",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast.show()
                                            }
                                            val intent = Intent(
                                                edtConfirmPassword.context,
                                                LoginActivity::class.java
                                            )
                                            startActivity(intent)

                                        } else {
                                            withContext(Dispatchers.Main) {
                                                // Số bạn nhập không phải số điện thoại
                                                val toast = Toast.makeText(
                                                    applicationContext,

                                                    "The number you entered is not a phone number",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast.show()
                                            }
                                        }

                                    } else {
                                        withContext(Dispatchers.Main) {
                                            //Nhập lại mật khẩu sai
                                            val toast = Toast.makeText(
                                                applicationContext,
                                                "Re-enter the wrong password",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.show()
                                        }
                                    }

                                } else {
                                    withContext(Dispatchers.Main) {
                                        //Mật khẩu phải có ít nhất 4 ký tự
                                        val toast = Toast.makeText(
                                            applicationContext,
                                            "Password must have at least 4 characters",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.show()
                                    }
                                }

                            } else {
                                withContext(Dispatchers.Main) {
                                    //Email đã được sử dụng
                                    val toast = Toast.makeText(
                                        applicationContext,
                                        "Email is already in use",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.show()
                                }
                            }

                        } else {
                            withContext(Dispatchers.Main) {
                                //Email không đúng định dạng
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Email invalidate",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            //Vui lòng nhập đầy đủ thông tin
                            val toast = Toast.makeText(
                                applicationContext,
                                "Please enter complete information",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    }
                }
            }

            tvRegister.setOnClickListener {
                val intent = Intent(edtConfirmPassword.context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
        fun isValidEmail(input: String?): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@gmail.com"
            val pattern = Pattern.compile(emailPattern)
            val matcher = pattern.matcher(input)
            return matcher.matches()
        }
        fun isValidPhoneNumber(input: String?): Boolean {
            val phoneNumberPattern = "^[0-9]{10}$"
            val pattern = Pattern.compile(phoneNumberPattern)
            val matcher = pattern.matcher(input)
            return matcher.matches()
        }



}