package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Movie
import com.example.movieapp.data.Seat

class MovieRevenueAdapter(private val movieList: List<Movie>?) :
    RecyclerView.Adapter<MovieRevenueAdapter.MovieRevenueViewHolder>() {
    inner class MovieRevenueViewHolder(viewSeat: View) : RecyclerView.ViewHolder(viewSeat) {
        val tvDisplayMovieName = viewSeat.findViewById<TextView>(R.id.tvDisplayMovieNameRevenue)
        val tvDisplayRevenue = viewSeat.findViewById<TextView>(R.id.tvDisplayRevenue)

        init {
        }
        fun onBindMovieRevenueViewHolder(movie: Movie) {
            tvDisplayMovieName.text = movie.movieName
            tvDisplayRevenue.text = movie.revenue.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRevenueAdapter.MovieRevenueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewSeat = inflater.inflate(R.layout.item_revenue_movie, parent, false)
        return MovieRevenueViewHolder(viewSeat)
    }

    override fun onBindViewHolder(holder: MovieRevenueAdapter.MovieRevenueViewHolder, position: Int) {
        holder.onBindMovieRevenueViewHolder(movieList?.get(position) ?: Movie(0,"",1,1,"",false,false,false,false,0.0,""))
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }
}