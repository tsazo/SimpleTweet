package com.codepath.tsazo.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


@Parcel // annotation indicates class is Parcelable
public class Movie {

    // Fields are must be public for parceler
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;
    Double popularity;
    Integer id;

    // no-arg, empty constructor required for Parceler
    public Movie() {}

    // Constructs Movie from values of inputted JSONObject
    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        // the example code has "voteAverage = movie.getDouble("vote_average");"
        voteAverage = jsonObject.getDouble("vote_average");
        popularity = jsonObject.getDouble("popularity");
        id = jsonObject.getInt("id");
    }

    // Create Movie array from JsonArray
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        // Hardcoded size - which can be fixed by getting size from API commands
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() { return popularity; }

    public Integer getId() {
        return id;
    }

    public String getMovieURL(){
        return String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
    }
}
