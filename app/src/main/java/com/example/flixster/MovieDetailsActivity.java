package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    //the movie to display
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster=(ImageView) findViewById(R.id.ivPoster);


        //retrieve, unwrap, and assign field from onCreate; unwrap the movie passed in via intent, using its simple name as a key.
        movie=(Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        //converting vote average to a 0-5 star rating
        float voteAverage=movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage/ 2.0f);

        String imageUrl;
        int placeholderUrl;
        placeholderUrl=R.drawable.flicks_backdrop_placeholder;

        Glide.with(this).load(movie.getBackdropPath()).placeholder(placeholderUrl).into(ivPoster);

    }
}