package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.Movie

class MovieAdapter: androidx.recyclerview.widget.ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieUtil()) {

    var onItemClick: (Movie)-> Unit={
    }


    inner class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imgMovie = view.findViewById<ImageView>(R.id.imgPhim)
        init {
           view.rootView.setOnClickListener {
               onItemClick.invoke(currentList[layoutPosition])
           }
       }

        fun onBind(movie: Movie){
            movie.img?.let { imgMovie.setImageResource(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class MovieUtil:androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return newItem.id == oldItem.id && newItem.favourite == oldItem.favourite
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return newItem == oldItem
    }

}