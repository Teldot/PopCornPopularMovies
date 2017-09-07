package com.example.android.popularmoviess1.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviess1.BuildConfig;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mauricio Torres on 10/08/2017.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String TMDB_API_LANG = "en-US";
    private static final String TMDB_PAGE = "1";

    private static final String Q_API_KEY = "api_key";
    private static final String Q_LANGUAGE = "language";
    private static final String Q_PAGE = "page";

    private static final String TMDB_IMG_URL = "http://image.tmdb.org/t/p/";
    private static final String TMDB_IMG_TBN_SM = "w185";
    private static final String TMDB_IMG_TBN_BG = "w500";


    /**
     * Method to build URL to get Now Playing Movies List.
     *
     * @return Formatted URL
     */
    public static URL buildUrl(String qAPI_URL) {
        Uri builtUri = Uri.parse(qAPI_URL).buildUpon()
                .appendQueryParameter(Q_API_KEY, BuildConfig.TMDB_API_KEY)
                .appendQueryParameter(Q_LANGUAGE, TMDB_API_LANG)
                .appendQueryParameter(Q_PAGE, TMDB_PAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "buildNowPlayingListUrl: " + url);

        return url;
    }

    /**
     * Method to build URL to get movies Images.
     *
     * @param imgName Poster name
     * @param isSmall Size of the Image
     * @return Formatted URL
     */
    public static URL buildURL(String imgName, boolean isSmall) {
        Uri builtUri = Uri.parse(TMDB_IMG_URL +
                (isSmall ? TMDB_IMG_TBN_SM : TMDB_IMG_TBN_BG) +
                imgName)
                .buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "buildImageURL: " + url);

        return url;
    }

    /**
     * Return string result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return String response.
     * @throws IOException
     */
    public static String getStringResponseFromHttpUrl(URL url) throws IOException {

        InputStream in = getStreamResponseFromHttpUrl(url);
        if (in == null) return null;
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    }

    /**
     * Return stream result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return Stream Response.
     * @throws IOException
     */
    private static InputStream getStreamResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = null;
        try {
            //return urlConnection.getInputStream();
            inputStream = (InputStream) urlConnection.getContent();
        } catch (Exception e) {
            Log.println(Log.DEBUG, TAG, e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
        return inputStream;
    }

    public static Bitmap loadImgFrom(URL url, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(context).load(url.toString()).get();
        } catch (Exception e) {
            Log.println(Log.DEBUG, TAG, e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return bitmap;
    }
}
