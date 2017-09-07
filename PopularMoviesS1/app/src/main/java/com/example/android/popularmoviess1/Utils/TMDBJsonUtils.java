package com.example.android.popularmoviess1.Utils;

import android.content.Context;

import com.example.android.popularmoviess1.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Mauricio Torres on 10/08/2017.
 */

class TMDBJsonUtils {
    public static MovieData[] getMovieListFromJson(String jsonData, Context context) throws JSONException, IOException {

        JSONObject mDataJson = new JSONObject(jsonData);
        JSONArray results = mDataJson.getJSONArray("results");

        MovieData[] movieData = new MovieData[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject item = results.getJSONObject(i);
            MovieData mItem = new MovieData(
                    item.getString("original_title"),
                    item.getString("release_date"),
                    item.getDouble("vote_average"),
                    item.getString("overview"),
                    item.getString("poster_path"),
                    item.getDouble("popularity")
            );
            URL imgUrl = NetworkUtils.buildURL(mItem.PosterFileName, true);

            mItem.PosterThumbnail = NetworkUtils.loadImgFrom(imgUrl, context);

            movieData[i] = mItem;
        }
        return movieData;
    }
}
