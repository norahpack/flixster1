package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

//parametrized by ViewHolder
//base Adapter is an abstract class;.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    //we need: context - where adapter is being constructed from
    //also data
    //These are member variables

    Context context;
    List<Movie> movies;

    private ItemMovieBinding binding;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies=movies;
    }

    //usually involves inflating a layout from xml and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }


    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the movie at the passed in position
        //bind the movie data into the view holder
        Log.d("MovieAdapter", "onBindViewHolder"+position);
        Movie movie=movies.get(position);
        holder.bind(movie);
    }


    //returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //represents one row in the View - one movie!
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemMovieBinding itemMovieBinding;

        //when the user clicks on a row, show MovieDetailsActivity for the selected movie.
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //makes sure position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION){
                //wouldn't work if the class were static
                Movie movie = movies.get(position);
                //create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public ViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());
            // itemView.setOnClickListener(this);
            itemView.getRoot().setOnClickListener(this);
            this.itemMovieBinding = itemView;
        }

        public void bind(Movie movie) {
            String rating;
            String color;
            if (movie.getVoteAverage()>=7.5){
                rating=" - HIGHLY RATED!";
                color="#07910c";
            } else if (movie.getVoteAverage()>=6){
                rating="";
                color="#070c91";
            } else {
                rating=" - AUDIENCE THUMBS DOWN";
                color="#ab0f07";
            }

            itemMovieBinding.tvTitle.setText((movie.getTitle().toUpperCase()).concat(rating));
            itemMovieBinding.tvTitle.setTextColor(Color.parseColor(color));
            itemMovieBinding.tvOverview.setText(movie.getOverview());
            String imageUrl;
            int placeholderUrl;
            if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageUrl=movie.getBackdropPath();
                placeholderUrl=R.drawable.flicks_backdrop_placeholder;
            } else {
                imageUrl=movie.getPosterPath();
                placeholderUrl=R.drawable.flicks_movie_placeholder;
            }
            int radius = 40;
            Glide.with(context).load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(radius))
                    .placeholder(placeholderUrl).into(itemMovieBinding.ivPoster);


        }
    }
}

