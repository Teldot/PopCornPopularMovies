package com.example.android.popularmoviess1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviess1.Utils.AsyncTaskCompleteListener;
import com.example.android.popularmoviess1.Utils.FetchDataTask;
import com.example.android.popularmoviess1.Utils.NetworkUtils;

import java.io.IOException;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView ivThumbnail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView tvName = (TextView) findViewById(R.id.movie_detail_name);
        ivThumbnail = (ImageView) findViewById(R.id.movie_detail_thumbnail);
        TextView tvReleaseDate = (TextView) findViewById(R.id.movie_detail_release_date);
        TextView tvRate = (TextView) findViewById(R.id.movie_detail_rate);
        TextView tvSynopsis = (TextView) findViewById(R.id.movie_detail_synopsis);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(getResources().getString(R.string.MOVIE_NAME)))
                tvName.setText(intent.getStringExtra(getResources().getString(R.string.MOVIE_NAME)));
            if (intent.hasExtra(getResources().getString(R.string.VOTE_RATE)))
                tvRate.setText(intent.getStringExtra(getResources().getString(R.string.VOTE_RATE)));
            if (intent.hasExtra(getResources().getString(R.string.RELEASE_DATE)))
                tvReleaseDate.setText(intent.getStringExtra(getResources().getString(R.string.RELEASE_DATE)));
            if (intent.hasExtra(getResources().getString(R.string.SYNOPSIS)))
                tvSynopsis.setText(intent.getStringExtra(getResources().getString(R.string.SYNOPSIS)));
            if (intent.hasExtra(getResources().getString(R.string.POSTER_URL))) {
                String posterName = intent.getStringExtra(getResources().getString(R.string.POSTER_URL));
                try {
                    getMoviePoster(posterName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void getMoviePoster(String imgName) throws IOException {
        Context context = this;
        new FetchDataTask(context,
                new FetchDataTaskCompleteListener(),
                FetchDataTask.DATATYPE_BITMAP,
                NetworkUtils.buildURL(imgName, false))
                .execute();
    }

    public class FetchDataTaskCompleteListener implements AsyncTaskCompleteListener<Object> {

        @Override
        public void onTaskComplete(Object result) {
            Bitmap bitmap = (Bitmap) result;
            ivThumbnail.setImageBitmap(bitmap);
        }

        @Override
        public void onPreExecute() {

        }
    }
}
