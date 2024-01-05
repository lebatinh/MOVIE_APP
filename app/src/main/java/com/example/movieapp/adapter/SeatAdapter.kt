package com.example.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Seat

class SeatAdapter(private val seatList: List<Seat>?) :
    RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    var onItemClickSeat: (Seat, Int) -> Unit = { a, b ->
    }
    inner class SeatViewHolder(viewSeat: View) : RecyclerView.ViewHolder(viewSeat) {
        val btnSeat = viewSeat.findViewById<ImageView>(R.id.btnSeat)
        init {
            btnSeat.setOnClickListener {
                onItemClickSeat.invoke(
                    seatList?.get(layoutPosition) ?: Seat(0,"", 4, false, false,"",""),
                    layoutPosition
                )

            }
        }
        val imgSeat: ImageView = viewSeat.findViewById(R.id.btnSeat)
        fun onBindSeat(seat: Seat) {
            Log.d("ANHTU1", seat.toString())
            seat.img?.let { imgSeat.setImageResource(it) }
            when(seat.checkSeat){
                true -> {
                    imgSeat.setImageResource(R.drawable.img_seat_available)

                }
                else -> {
                    imgSeat.setImageResource(R.drawable.img_seat_reserved)
                    if (seat.isSeat == true) {
                        imgSeat.setImageResource(R.drawable.img_seat_selected)
                    } else {
                        imgSeat.setImageResource(R.drawable.img_seat_reserved)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewSeat = inflater.inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(viewSeat)
    }
    override fun getItemCount(): Int {
        return seatList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.onBindSeat(seatList?.get(position) ?: Seat(0,"", 4,  false, false,"",""))
    }
}