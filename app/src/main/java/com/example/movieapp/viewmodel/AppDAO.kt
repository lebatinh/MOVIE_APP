package com.example.movieapp.viewmodel

import androidx.room.*
import com.example.movieapp.data.Category
import com.example.movieapp.data.Movie
import com.example.movieapp.data.Seat
import com.example.movieapp.data.User

@Dao
interface AppDAO {
    @Query("SELECT * FROM tb_movie")
    fun getMovieList(): List<Movie>?

    @Insert
    fun insertMovie(movie: Movie)

    @Insert
    fun insertMovieList(list: List<Movie>)

    @Query("SELECT * FROM tb_movie WHERE movieName = :movieName")
    fun getMovie(movieName: String): Movie?

    @Query("UPDATE tb_movie SET revenue = :updatedRevenue WHERE movieName = :movieName")
    fun updateRevenueMovie(updatedRevenue: Double, movieName: String)

    @Query("UPDATE tb_movie SET revenue = :updatedRevenue")
    fun updateRevenue(updatedRevenue: Double)

    @Query("DELETE FROM tb_movie WHERE id = :id")
    fun deleteMovie(id: Int)

    @Query("SELECT * FROM tb_category")
    fun getCategory(): List<Category>?

    @Insert
    fun insertCategory(category: Category)

    @Insert
    fun insertListCategory(list: List<Category>)

    @Query("SELECT * FROM tb_seat WHERE movieName = :movieName")
    fun getListSeatFromMovie(movieName: String): List<Seat>?

    @Query("SELECT * FROM tb_seat WHERE user = :userName AND checkSeat = :checkSeat")
    fun getListSeatSelectFromUser(userName: String,checkSeat: Boolean): List<Seat>?

    @Query("SELECT * FROM tb_seat WHERE movieName = :movieName AND checkSeat = :checkSeat")
    fun getListSeatSelectFromMovieName(movieName: String,checkSeat: Boolean): List<Seat>?

    @Query("SELECT * FROM tb_seat WHERE movieName = :movieName AND user = :userName AND checkSeat = :checkSeat")
    fun getListSeatWhereMovieUser(movieName: String, userName: String,checkSeat: Boolean): List<Seat>?

    @Insert
    fun insertListSeat(list: List<Seat>)

    @Query("UPDATE tb_seat SET checkSeat = :checkSeat , user = :user WHERE movieName = :movieName AND seatNumber = :seatNumber")
    fun updateCheckSeat(user: String,checkSeat: Boolean, seatNumber: Int, movieName: String)

    @Query("UPDATE tb_seat SET checkSeat = :checkSeat, user = :user")
    fun updateSeat(checkSeat: Boolean,user: String)

    @Query("UPDATE tb_seat SET checkSeat = :checkSeat WHERE user = :user")
    fun updateCheckSeatFromAdmin(checkSeat: Boolean, user :String)

    @Query("DELETE FROM tb_seat WHERE movieName = :movieName AND seatNumber = :seatNumber")
    fun deleteSeat(movieName: String, seatNumber: Int)

    @Insert
    fun insertSeat(seat: Seat)

    @Query("SELECT * FROM tb_user")
    fun getUserList(): List<User>?

    @Query("SELECT * FROM tb_user WHERE emailName = :emailName")
    fun getUser(emailName: String): User?



    @Query("UPDATE tb_user SET totalSpending = :totalSpending WHERE emailName = :emailName")
    fun updatetotalSpendingUser(totalSpending: Double?, emailName: String?)

    @Query("UPDATE tb_user SET money = :money WHERE emailName = :emailName")
    fun updateMoneyUser(money: Double?, emailName: String?)

    @Query("DELETE FROM tb_user WHERE emailName = :emailName")
    fun deleteUser(emailName: String)

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT COUNT(*) FROM tb_user WHERE emailName = :itemEmailName")
    fun checkEmail(itemEmailName: String): Boolean?

    @Query("SELECT COUNT(*) FROM tb_user WHERE emailName = :emailName AND password = :password")
    fun login(emailName: String, password: String): Boolean?

    @Query("SELECT COUNT(*) FROM tb_user WHERE emailName = :emailName AND phoneNumber = :phoneNumber")
    fun resetPassword(emailName: String, phoneNumber: String): Boolean?

    @Query("UPDATE tb_user SET password = :password WHERE emailName = :emailName")
    fun updatePassword(password:String, emailName: String)
}