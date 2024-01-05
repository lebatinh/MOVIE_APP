package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Seat

class SeatSelectMainUserAdapter(private val seatListMainUser: List<Seat>?) :
    RecyclerView.Adapter<SeatSelectMainUserAdapter.SeatSelectMainUserHolder>() {
    var onItemClickUser: (Seat) -> Unit = { a ->
    }
    var onClickDeleteTicket: (Seat) -> Unit = { Seat ->
    }
    inner class SeatSelectMainUserHolder(viewSeat: View) : RecyclerView.ViewHolder(viewSeat) {
        val tvDisplayMovieNameMainUser = viewSeat.findViewById<TextView>(R.id.tvDisplayMovieNameMainUser)
        val tvDisplaySeatNumberMainUser = viewSeat.findViewById<TextView>(R.id.tvDisplaySeatNumberMainUser)
        val tvDisplayShowtimeMainUser = viewSeat.findViewById<TextView>(R.id.tvDisplayShowTime)
        val btnDeleteTicket = viewSeat.findViewById<ImageButton>(R.id.btnDeleteTicket)

        init {
            btnDeleteTicket.setOnClickListener {
                onClickDeleteTicket.invoke(
                    seatListMainUser?.get(layoutPosition) ?: Seat(0,"", 4, false, false,"","")
                )
            }
        }
        fun onBindSeatSelectMainUser(seat: Seat) {
            tvDisplayMovieNameMainUser.text = seat.movieName
            tvDisplaySeatNumberMainUser.text = seat.seatNumber.toString()
            tvDisplayShowtimeMainUser.text = seat.showtime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatSelectMainUserAdapter.SeatSelectMainUserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewSeat = inflater.inflate(R.layout.item_seat_main_user, parent, false)
        return SeatSelectMainUserHolder(viewSeat)
    }

    override fun onBindViewHolder(holder: SeatSelectMainUserAdapter.SeatSelectMainUserHolder, position: Int) {
        holder.onBindSeatSelectMainUser(seatListMainUser?.get(position) ?: Seat(0,"",0,true,true,"",""))
    }

    override fun getItemCount(): Int {
        return seatListMainUser?.size ?: 0
    }
}