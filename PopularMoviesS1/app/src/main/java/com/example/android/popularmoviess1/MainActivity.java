package com.example.android.popularmoviess1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviess1.Utils.AsyncTaskCompleteListener;
import com.example.android.popularmoviess1.Utils.FetchDataTask;
import com.example.android.popularmoviess1.Utils.NetworkUtils;

import static com.example.android.popularmoviess1.MovieListAdapter.*;

/**
 * Created by Mauricio Torres on 10/08/2017.
 */
public class MainActivity extends AppCompatActivity implements MovieListAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private MovieListAdapter mlAdapter;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        LinearLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(this, 3);
        else
            layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mlAdapter = new MovieListAdapter(this);
        mRecyclerView.setAdapter(mlAdapter);

        loadMoviesInfo(getResources().getString(R.string.TMDB_API_NOW));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sel = item.getItemId();
        switch (sel) {
            case R.id.action_odr_by_popular:
                mlAdapter.setMovieListData(null);
                loadMoviesInfo(getResources().getString(R.string.TMDB_API_POP));

                //ORDER CURRENT DATA BY POPULARITY
                //if (moviesData == null) break;
                //Arrays.sort(moviesData, new ShortByPopular());
                //mlAdapter.setMovieListData(moviesData);
                break;
            case R.id.action_ord_by_h_rate:
                mlAdapter.setMovieListData(null);
                loadMoviesInfo(getResources().getString(R.string.TMDB_API_RATE));

                //ORDER CURRENT DATA BY RATE
                //if (moviesData == null) break;
                //Arrays.sort(moviesData, new ShortByRate());
                //mlAdapter.setMovieListData(moviesData);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoviesInfo(String qAPI_URL) {
        showMoviesDataView();
        new FetchDataTask(this,
                new FetchDataTaskCompleteListener(),
                FetchDataTask.DATATYPE_OBJECT,
                NetworkUtils.buildUrl(qAPI_URL))
                .execute();
    }


    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(MovieData movieData) {
        Context context = this;
        try {
            Class destinationClass = MovieDetailActivity.class;
            Intent intent = new Intent(context, destinationClass);
            intent.putExtra(getResources().getString(R.string.MOVIE_NAME), movieData.Name);
            intent.putExtra(getResources().getString(R.string.POSTER_URL), movieData.PosterFileName);
            intent.putExtra(getResources().getString(R.string.RELEASE_DATE), movieData.ReleaseDate);
            intent.putExtra(getResources().getString(R.string.VOTE_RATE), movieData.VoteRate.toString());
            intent.putExtra(getResources().getString(R.string.SYNOPSIS), movieData.Synopsis);
            startActivity(intent);
        } catch (Exception e) {
            Log.println(Log.DEBUG, "", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }

    }

    public class FetchDataTaskCompleteListener implements AsyncTaskCompleteListener<Object> {
        @Override
        public void onTaskComplete(Object result) {
            MovieData[] moviesData = (MovieData[]) result;
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                showMoviesDataView();
                mlAdapter.setMovieListData(moviesData);
            } else {
                showErrorMessage();
            }
        }

        @Override
        public void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }


}
