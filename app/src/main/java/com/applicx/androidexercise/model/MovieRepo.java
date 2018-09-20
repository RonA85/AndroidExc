package com.applicx.androidexercise.model;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.applicx.androidexercise.Cons;
import com.applicx.androidexercise.api.ApiService;
import com.applicx.androidexercise.api.RetrofitClient;

import java.security.acl.LastOwnerException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {
    private final String TAG = getClass().getSimpleName();
    private ApiService apiService;

    public MovieRepo() {
        this.apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public void loadTopRatedMovies(final MutableLiveData<MoviesResponse> liveData){
        Call<MoviesResponse> call = apiService.getTopMovies(Cons.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    public void loadMovieDetails(final MutableLiveData<Movie> liveData,final int id){
        Call<Movie> call = apiService.getDetails(id,Cons.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    public void loadMovieCast(final MutableLiveData<CastResponse> liveData,final int id){
        Call<CastResponse> call = apiService.getCast(id,Cons.API_KEY);
        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                Log.d(TAG,"respone massage: "+ response.body().getCasts().size());
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }
}
