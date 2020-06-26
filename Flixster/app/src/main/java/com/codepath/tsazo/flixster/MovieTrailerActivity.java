package com.codepath.tsazo.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.tsazo.flixster.adapters.MovieAdapter;
import com.codepath.tsazo.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    final static String TAG = "MovieTrailerActivity";
    Movie movie;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // Gets movie url from intent (in MovieDetailsActivity)
        final String MOVIE_URL =  getIntent().getStringExtra("movieURL");

        Log.d(TAG, String.format("Movie URL is: '%s'", MOVIE_URL));

        // AsyncHttpClient for Youtube API
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(MOVIE_URL, new JsonHttpResponseHandler() {
            // Called if client can successfully access the API/database
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try{
                    // JSON results = movies and their data
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    JSONObject movieVideo = (JSONObject) results.get(0);

                    videoId = movieVideo.getString("key");

                    Log.d(TAG, "Video KEY is: " + videoId);

                } catch (JSONException e){
                    Log.e(TAG, "Hit json exception");
                }

                playYouTubeVideo();

            }

            // Called if client fails to access the API/database
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void playYouTubeVideo(){
        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                Log.d(TAG, "SO CLOSE AHHHH " + videoId);

                youTubePlayer.loadVideo(videoId);
                //youTubePlayer.cueVideo("SUXWAEX2jlg");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}