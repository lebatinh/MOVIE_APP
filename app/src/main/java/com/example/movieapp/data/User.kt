package com.example.movieapp.data

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.Money

@Entity(tableName = "tb_user")
data class User (
        @PrimaryKey (autoGenerate = true) val id: Int = 0,
        val emailName: String?,
        val password: String?,
        val phoneNumber: String?,
        val nickname: String?,
        val totalSpending: Double = 0.0,
        val money: Double? = 0.0,
        )