package com.example.android.popularmoviess1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by Mauricio Torres on 10/08/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListAdapterViewHolder> {
    private MovieData[] moviesData;
    private static final boolean shouldAttachToParentImmediately  = false;

    private final MovieListAdapterOnClickHandler mClickHandler;

    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, shouldAttachToParentImmediately);
        return new MovieListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapterViewHolder holder, int position) {
        holder.mItemPoster.setImageBitmap(moviesData[position].PosterThumbnail);
        /*holder.mItemName.setText(moviesData[position].Name);
        holder.mItemRate.setText(moviesData[position].VoteRate.toString());
        holder.mItemPop.setText(moviesData[position].Popularity.toString());*/
        boolean pos = position % 2 == 0;
        holder.mItemCont.setBackgroundResource(pos ? R.color.list_item_even : R.color.list_item_odd);
    }

    @Override
    public int getItemCount() {
        if (moviesData == null) return 0;
        return moviesData.length;
    }

    public void setMovieListData(MovieData[] mdata) {
        moviesData = mdata;
        notifyDataSetChanged();
    }

    public interface MovieListAdapterOnClickHandler {
        void onClick(MovieData movieData);
    }

    public MovieListAdapter(MovieListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mItemPoster;
        public final LinearLayout mItemCont;
        /*public final TextView mItemName;
        public final TextView mItemRate;
        public final TextView mItemPop;*/

        public MovieListAdapterViewHolder(View view) {
            super(view);
            mItemPoster = (ImageView) view.findViewById(R.id.list_movie_thumbnail);
            mItemCont = (LinearLayout) view.findViewById(R.id.list_item_cont);
            /*mItemName = (TextView) view.findViewById(R.id.list_item_name);
            mItemRate = (TextView) view.findViewById(R.id.list_item_rate);
            mItemPop = (TextView) view.findViewById(R.id.list_item_pop);*/
            view.setOnClickListener(this);
        }


        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieData mdata = moviesData[adapterPosition];

            mClickHandler.onClick(mdata);
        }
    }

}
