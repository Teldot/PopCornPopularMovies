package com.example.android.popularmoviess1.Utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by US on 13/08/2017.
 */

public class FetchDataTask extends AsyncTask {
    public static final int DATATYPE_STRING = 0;
    public static final int DATATYPE_BITMAP = 1;
    public static final int DATATYPE_OBJECT = 2;

    private final int Task;
    private final Context context;
    private final AsyncTaskCompleteListener<Object> listener;
    private final URL requestUrl;

    public FetchDataTask(Context _context, AsyncTaskCompleteListener<Object> _listener, int _task, URL _url) {
        this.context = _context;
        this.listener = _listener;
        this.Task = _task;
        this.requestUrl = _url;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        switch (Task) {
            case DATATYPE_STRING:
                try {
                    return NetworkUtils.getStringResponseFromHttpUrl(requestUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case DATATYPE_BITMAP:
                return NetworkUtils.loadImgFrom(requestUrl, context);
            case DATATYPE_OBJECT:
                try {
                    String jsontmdbResponse = NetworkUtils.getStringResponseFromHttpUrl(requestUrl);
                    if (jsontmdbResponse == null || jsontmdbResponse.length() == 0) break;
                    return TMDBJsonUtils.getMovieListFromJson(jsontmdbResponse, context);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            default:
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        listener.onTaskComplete(o);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPreExecute();
    }
}
