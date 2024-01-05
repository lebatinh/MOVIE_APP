package com.example.movieapp.activity

import android.Manifest
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movieapp.AppDatabase
import com.example.movieapp.R
import com.example.movieapp.viewmodel.AppDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import kotlin.math.log
import kotlin.random.Random

class ResetPasswordActivity : AppCompatActivity() {

    private var verificationRandom: String? = null
    private var confirm: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_activty)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnReset = findViewById<Button>(R.id.btnResetPassword)
        val btnBack = findViewById<ImageButton>(R.id.btnBackResetPassword)
        val edtPhoneNumber = findViewById<EditText>(R.id.edtPhoneNumbertResetPassword)
        val edtEmailName = findViewById<EditText>(R.id.edtEmailNameResetPassword)
        val edtVerificationCode = findViewById<EditText>(R.id.edtVerificationCode)
        val edtNewPassword = findViewById<EditText>(R.id.edtNewPassword)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val appDAO: AppDAO = db.appDao()

        btnConfirm.setOnClickListener {

            val emailName = edtEmailName.text.toString()
            val phoneNumber = edtPhoneNumber.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                val userEmail = appDAO.resetPassword(emailName, phoneNumber)

                if (emailName.isEmpty() && phoneNumber.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        //Bạn nhập thiếu thông tin
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "You entered missing information",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (userEmail == true) {
                        withContext(Dispatchers.Main) {
                            //Mời nhập mã xác nhận và mật khẩu mới
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Please enter the confirmation code and new password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (phoneNumber.trim().isEmpty()) {
                            //Bạn chưa nhập số điện thoại
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "You did not enter a phone number",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (isPhoneNumber(phoneNumber)) {
                                confirm = true
                                verificationRandom = generateRandomString()
                                Log.d("reset", verificationRandom!!)
                                val m = SmsManager.getDefault()
                                //Mã xác nhận của bạn là
                                val SMS = "Your confirmation code is: $verificationRandom"
                                m.sendTextMessage(phoneNumber, null, SMS, null, null)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@ResetPasswordActivity,
                                        "Please see the results via SMS",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@ResetPasswordActivity,
                                        "The number you entered is not a phone number",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        withContext(Dispatchers.Main) {
                            edtVerificationCode.visibility = View.VISIBLE
                            edtNewPassword.visibility = View.VISIBLE
                        }
                    }

                         withContext(Dispatchers.Main) {
                             //Tài khoản không tồn tại
                             Toast.makeText(
                                 this@ResetPasswordActivity,
                                 "Account does not exist",
                                 Toast.LENGTH_SHORT
                             ).show()
                         }

                }
            }
        }

        btnReset.setOnClickListener {
            val emailName = edtEmailName.text.toString()
            val phoneNumber = edtPhoneNumber.text.toString()
            val verificationCode = edtVerificationCode.text.toString()
            val newPassword = edtNewPassword.text.toString()
            if (emailName.isEmpty() && phoneNumber.isEmpty()){
                //Bạn nhập thiếu thông tin
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "You entered missing information",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (confirm == true) {
                    if (verificationCode.isEmpty()) {
                        //Mời nhập mã xác nhận
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Please enter confirmation code",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (newPassword.isEmpty()) {
                            if (verificationCode.isEmpty()) {
                                //Mời nhập sdt
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "Please enter new Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                        if (verificationRandom != null && verificationCode == verificationRandom) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                Log.d("reset", newPassword)
                                appDAO.updatePassword(newPassword, emailName)
                            }
                            //Đổi mật khẩu thành công
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Password changed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            //Mã xác nhận sai
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Verification code is wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    }
            } else {
                //vui lòng xac nhận
                    Toast.makeText(
                        this@ResetPasswordActivity,
                        "Please confirm",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        ActivityCompat.requestPermissions(
            this@ResetPasswordActivity, arrayOf<String>(
                Manifest.permission.SEND_SMS
            ), 0
        )
    }

    private fun isPhoneNumber(input: String?): Boolean {
        val phonePattern = "^0\\d{9}$"
        val pattern = Pattern.compile(phonePattern)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun generateRandomString(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..4)
            .map { charPool[Random.nextInt(0, charPool.size)] }
            .joinToString("")
    }
}
