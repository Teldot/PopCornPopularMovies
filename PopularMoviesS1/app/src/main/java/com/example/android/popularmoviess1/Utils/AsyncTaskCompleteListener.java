package com.example.android.popularmoviess1.Utils;

/**
 * Created by Mauricio Torres on 13/08/2017.
 */

public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);
    void onPreExecute();
}
