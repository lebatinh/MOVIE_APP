package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Seat


class SeatSelecterFromUserAdapter(private val seatList: List<Seat>?) :
    RecyclerView.Adapter<SeatSelecterFromUserAdapter.SeatSelecterFromUserViewHolder>() {
    inner class SeatSelecterFromUserViewHolder(viewSeat: View) : RecyclerView.ViewHolder(viewSeat) {
        val tvDisplayMovieName = viewSeat.findViewById<TextView>(R.id.tvDisplayMovieName)
        val tvDisplaySeatNumber = viewSeat.findViewById<TextView>(R.id.tvDisplaySeatNumber)

        init {
        }
        fun onBindSeatSelecterFromUser(seat: Seat) {
            tvDisplayMovieName.text = seat.movieName
            tvDisplaySeatNumber.text = seat.seatNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatSelecterFromUserAdapter.SeatSelecterFromUserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewSeat = inflater.inflate(R.layout.item_seat_selected_from_user, parent, false)
        return SeatSelecterFromUserViewHolder(viewSeat)
    }

    override fun onBindViewHolder(holder: SeatSelecterFromUserAdapter.SeatSelecterFromUserViewHolder, position: Int) {
        holder.onBindSeatSelecterFromUser(seatList?.get(position) ?: Seat(0,"",0,true,true,"",""))
    }

    override fun getItemCount(): Int {
        return seatList?.size ?: 0
    }
}