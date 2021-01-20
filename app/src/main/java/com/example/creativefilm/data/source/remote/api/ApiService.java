package com.example.creativefilm.data.source.remote.api;

import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.MovieResponse;
import com.example.creativefilm.data.source.remote.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvShowResponse> getTopRatedTvShows(@Query("api_key") String apiKey);

    @GET("tv/{id}")
    Call<TvShowDetail> getTvShowsDetails(@Path("id") int id, @Query("api_key") String apiKey);


}
