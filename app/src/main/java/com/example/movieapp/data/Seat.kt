package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tb_seat")
data class Seat(
    @PrimaryKey(autoGenerate = true) val seatNumber: Int = 0,// Số thứ tự của ghế
    var movieName: String? = null, // Xác định bộ phim mà ghế thuộc về
    var img: Int,
    var isSeat: Boolean, // Trạng thái của ghế (đã đặt hoặc chưa đặt)
    var checkSeat: Boolean,
    var user: String? = null,
    val showtime: String?,
)