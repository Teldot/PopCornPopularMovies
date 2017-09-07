package com.example.android.popularmoviess1.Utils;

import com.example.android.popularmoviess1.MovieData;

import java.util.Comparator;

/**
 * Created by Mauricio Torres on 11/08/2017.
 */

public class ShortByRate implements Comparator<MovieData> {

    /**
     * Compare if two elements and returns the difference
     * @param o1 First element to compare
     * @param o2 Second element to compare
     * @return Difference between elements
     */
    @Override
    public int compare(MovieData o1, MovieData o2) {
        Double iO1 = o1.VoteRate * 10;
        Double iO2 = o2.VoteRate * 10;
        return iO2.intValue() - iO1.intValue();
    }

}
