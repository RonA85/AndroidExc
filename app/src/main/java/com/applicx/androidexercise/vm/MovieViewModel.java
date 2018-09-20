package com.applicx.androidexercise.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.applicx.androidexercise.model.CastResponse;
import com.applicx.androidexercise.model.Movie;
import com.applicx.androidexercise.model.MovieRepo;
import com.applicx.androidexercise.model.MoviesResponse;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MoviesResponse> moviesLiveData;
    private MutableLiveData<CastResponse> castLiveData;
    private MutableLiveData<Movie> movieLiveData;
    private MovieRepo movieRepo;

    public MovieViewModel(){
        this.moviesLiveData = new MutableLiveData<>();
        this.movieLiveData = new MutableLiveData<>();
        this.castLiveData = new MutableLiveData<>();
        this.movieRepo = new MovieRepo();
    }

    public LiveData<MoviesResponse> getMoviesLiveData() {
        return moviesLiveData;
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<CastResponse> getCastLiveData() {
        return castLiveData;
    }

    public void loadTopRatedMovies(){
        movieRepo.loadTopRatedMovies(moviesLiveData);
    }

    public void loadMovieDetails(int id){
        movieRepo.loadMovieDetails(movieLiveData,id);
        movieRepo.loadMovieCast(castLiveData,id);
    }
}
