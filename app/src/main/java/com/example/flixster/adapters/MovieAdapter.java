package com.example.flixster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

//parametrized by ViewHolder
//base Adapter is an abstract class;.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    //we need: context - where adapter is being constructed from
    //also data
    //These are member variables

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies=movies;
    }

    //usually involves inflating a layout from xml and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView= LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
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
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            ivPoster=itemView.findViewById(R.id.ivPoster);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}
