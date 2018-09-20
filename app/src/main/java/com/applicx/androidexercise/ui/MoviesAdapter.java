package com.applicx.androidexercise.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicx.androidexercise.Cons;
import com.applicx.androidexercise.R;
import com.applicx.androidexercise.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {

    private final String TAG = getClass().getSimpleName();

    private List<Movie> originalData;
    private List<Movie> filterData ;
    private Context context;
    private OnMovieClickListener listener;
    private String filterString;


    public interface OnMovieClickListener {
        void onClickMovie(int id);
    }

    public MoviesAdapter(Context context, List<Movie> movies, OnMovieClickListener listener) {
        this.context = context;
        originalData = movies;
        filterData = movies;
        this.listener = listener;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        final Movie movie = filterData.get(position);
        holder.title.setText(movie.getTitle());
       // holder.rate.setText(movie.getRate());
        holder.releaseYear.setText(movie.getRealasedate());
        holder.overview.setText(movie.getOverview());
        Glide.with(context).load(Cons.BASE_PATH + movie.getPoster()).into(holder.poster);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickMovie(movie.getId());
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                filterString = constraint.toString().toLowerCase();
                final List<Movie> filterList = new ArrayList<>();
                for (int i = 0; i < originalData.size(); i++) {
                    if (originalData.get(i).getTitle().toLowerCase().contains(filterString))
                        filterList.add(originalData.get(i));
                }
                results.values = filterList;
                results.count = filterList.size();
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterData = (List<Movie>) filterResults.values;
                Log.d("MoviesAdapter", filterData.get(0).getTitle());
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public int getItemCount() {
        return filterData != null ? filterData.size() : 0;
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_movie)
        CardView card;
        @BindView(R.id.iv_movie_poster)
        ImageView poster;
        @BindView(R.id.tv_movie_title)
        TextView title;
        @BindView(R.id.tv_movie_rate)
        TextView rate;
        @BindView(R.id.tv_movie_release_year)
        TextView releaseYear;
        @BindView(R.id.tv_movie_overview)
        TextView overview;

        private MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
