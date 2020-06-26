package com.codepath.tsazo.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.tsazo.flixster.MovieDetailsActivity;
import com.codepath.tsazo.flixster.R;
import com.codepath.tsazo.flixster.models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.codepath.tsazo.flixster.R.id.ivPoster;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);

        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        /// Instantiate the RecyclerViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            //
            itemView.setOnClickListener(this);
        }

        // Bind the values to the ViewHolder
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int placeholder;

            // If phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;

            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }

            // Use Glide to have image load into activity
            int radius = 40; // corner radius, higher value = more rounded
            int margin = 0; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholder) // loads place holder image
                    .fitCenter() // retains size of images
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);

        }

        // when user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View view) {
            // Gets the item position
            int position = getAdapterPosition();

            // make sure the position is valid, i.e. exists in the view
            if (position != RecyclerView.NO_POSITION){

                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                // serialize the movie using parceler, use its short name as a key
                // First string is the name, second string is the value
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                // show the activity
                context.startActivity(intent);
            }
        }
    }


}
