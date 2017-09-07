package com.example.android.popularmoviess1;

import android.graphics.Bitmap;

/**
 * Created by Mauricio Torres on 09/08/2017.
 */

public class MovieData {
    public final String Name;
    public final String ReleaseDate;
    public final Double VoteRate;
    public final Double Popularity;
    public final String Synopsis;
    public Bitmap PosterThumbnail;
    public final String PosterFileName;

    public MovieData(String name, String releaseDate, Double voteRate, String synopsis, String posterFileName, Double popularity) {
        this.Name = name;
        this.ReleaseDate = releaseDate;
        this.VoteRate = voteRate;
        this.Synopsis = synopsis;
        this.PosterFileName = posterFileName;
        this.Popularity = popularity;
    }
}
