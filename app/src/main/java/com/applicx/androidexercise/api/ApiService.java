package com.applicx.androidexercise.api;

import com.applicx.androidexercise.model.CastResponse;
import com.applicx.androidexercise.model.Movie;
import com.applicx.androidexercise.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top_rated")
    Call<MoviesResponse> getTopMovies(@Query("api_key") String api_key);

    @GET("{movie_id}")
    Call<Movie> getDetails(@Path("movie_id") int id, @Query("api_key") String api_key);

    @GET("{movie_id}/credits")
    Call<CastResponse> getCast(@Path("movie_id") int id, @Query("api_key") String api_key);

}
