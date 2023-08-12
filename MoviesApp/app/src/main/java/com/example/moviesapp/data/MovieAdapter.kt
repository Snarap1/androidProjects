package com.example.moviesapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviesapp.R
import com.example.moviesapp.models.Movie
import com.squareup.picasso.Picasso

class MovieAdapter (var context:Context, var movies:ArrayList<Movie>):
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    public  class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var posterImageView:ImageView
        lateinit var titleTextView:TextView
        lateinit var yearTextView:TextView

        init {
            posterImageView = itemView.findViewById(R.id.posterImageVIew)
            titleTextView = itemView.findViewById(R.id.titleTextView)
            yearTextView = itemView.findViewById(R.id.yearTextView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie:Movie = movies[position]

        val title:String = currentMovie.title
        val year = currentMovie.year
        val posterUrl = currentMovie.posterUrl

        holder.titleTextView.setText(title)
        holder.yearTextView.setText(year)
        Picasso.get().load(posterUrl).fit().centerInside().into(holder.posterImageView)
    }


}