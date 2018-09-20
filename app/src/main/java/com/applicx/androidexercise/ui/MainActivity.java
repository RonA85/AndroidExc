package com.applicx.androidexercise.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.applicx.androidexercise.R;
import com.applicx.androidexercise.model.Movie;
import com.applicx.androidexercise.model.MoviesResponse;
import com.applicx.androidexercise.vm.MovieViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher {

    @BindView(R.id.et_search)
    MaterialEditText etSearch;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private Unbinder unbinder;
    private MoviesAdapter moviesAdapter;
    public static String MOVIE_ID = "movie_id";
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        setupRecycler();
//        moviesAdapter = new MoviesAdapter(MainActivity.this, MainActivity.this);
        swipe.setOnRefreshListener(this);
        etSearch.addTextChangedListener(this);
        movieViewModel.getMoviesLiveData().observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(@Nullable MoviesResponse moviesResponse) {
                // create an empty adapter and add it to the recycler view
                // Reading all movies
                //    List<Movie> movies = Movie.listAll(Movie.class);
                swipe.setRefreshing(false);
                assert moviesResponse != null;
                List<Movie> movies = moviesResponse.getResults();
                moviesAdapter = new MoviesAdapter(MainActivity.this,movies ,MainActivity.this);
               // moviesAdapter.updateItems(movies);
                rvMovies.setAdapter(moviesAdapter);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        movieViewModel.loadTopRatedMovies();
    }

    private void setupRecycler() {
        rvMovies.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(layoutManager);

    }

    @Override
    public void onClickMovie(int id) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, id);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        movieViewModel.loadTopRatedMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        moviesAdapter.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
