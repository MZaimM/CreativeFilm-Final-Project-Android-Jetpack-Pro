package com.example.creativefilm.utils;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.api.ApiConfig;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.data.source.remote.response.MovieResponse;
import com.example.creativefilm.data.source.remote.response.TvShowResponse;
import com.example.creativefilm.ui.detail.DetailViewModel;
import com.example.creativefilm.ui.movies.MoviesViewModel;
import com.example.creativefilm.ui.tvshow.TvShowsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonHelper {
    public static final String API_KEY = "21591d621aa30858c2fd62100681e1da";
    private final Handler handler = new Handler();
    public static final Integer TIME = 5000;

    public JsonHelper() {
    }


    public LiveData<ApiResponse<List<Movie>>> getMovie(MutableLiveData<ApiResponse<List<Movie>>> listMovie){
        EspressoIdlingResource.increment();
        handler.postDelayed(() -> {
            Call<MovieResponse> call = ApiConfig.getApiService().getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            listMovie.postValue(ApiResponse.success(response.body().getResults()));
                        }
                    }else {
                        Log.e(MoviesViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    Log.e(MoviesViewModel.class.getSimpleName(),t.toString());
                }
            });
        },TIME);
        return listMovie;
    }

    public LiveData<ApiResponse<List<TvShow>>> getTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listTvShows){
        EspressoIdlingResource.increment();
        handler.postDelayed(() -> {
            Call<TvShowResponse> call = ApiConfig.getApiService().getTopRatedTvShows(API_KEY);
            call.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(@NotNull Call<TvShowResponse> call, @NotNull Response<TvShowResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            listTvShows.postValue(ApiResponse.success(response.body().getResults()));
                        }
                    }else {
                        Log.e(TvShowsViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }
                }
                @Override
                public void onFailure(@NotNull Call<TvShowResponse> call, @NotNull Throwable t) {
                    Log.e(TvShowsViewModel.class.getSimpleName(),t.toString());
                }
            });
        },TIME);

        return listTvShows;
    }

    public LiveData<ApiResponse<MovieDetail>> getMoviesDetail(MutableLiveData<ApiResponse<MovieDetail>> movieDetail, Integer movieId){
        EspressoIdlingResource.increment();
        handler.postDelayed(() -> {
            Call<MovieDetail> call = ApiConfig.getApiService().getMovieDetails(movieId,API_KEY);
            call.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(@NotNull Call<MovieDetail> call, @NotNull Response<MovieDetail> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            movieDetail.postValue(ApiResponse.success(response.body()));
                        }

                    }else {
                        Log.e(DetailViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }

                }
                @Override
                public void onFailure(@NotNull Call<MovieDetail> call, @NotNull Throwable t) {
                    Log.e(DetailViewModel.class.getSimpleName(),t.toString());
                }
            });
        },TIME);
        return movieDetail;
    }

    public LiveData<ApiResponse<TvShowDetail>> getTvShowsDetail(MutableLiveData<ApiResponse<TvShowDetail>> tvShowDetail, Integer tvShowId){
        EspressoIdlingResource.increment();
        handler.postDelayed(()->{
            Call<TvShowDetail> call = ApiConfig.getApiService().getTvShowsDetails(tvShowId,API_KEY);
            call.enqueue(new Callback<TvShowDetail>() {
                @Override
                public void onResponse(@NotNull Call<TvShowDetail> call, @NotNull Response<TvShowDetail> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            tvShowDetail.postValue(ApiResponse.success(response.body()));
                        }
                    }else {
                        Log.e(DetailViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TvShowDetail> call, @NotNull Throwable t) {
                    Log.e(DetailViewModel.class.getSimpleName(),t.toString());
                }
            });

        },TIME);
        return tvShowDetail;
    }

    public LiveData<ApiResponse<List<Movie>>> getOtherMovie(MutableLiveData<ApiResponse<List<Movie>>> listOtherMovie){
        EspressoIdlingResource.increment();
        handler.postDelayed(()->{
            Call<MovieResponse> call = ApiConfig.getApiService().getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            listOtherMovie.postValue(ApiResponse.success(response.body().getResults()));
                        }
                    }else {
                        Log.e(DetailViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    Log.e(DetailViewModel.class.getSimpleName(),t.toString());
                }
            });
        },TIME);
        return listOtherMovie;
    }

    public LiveData<ApiResponse<List<TvShow>>> getOtherTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listOtherTvShow){
        EspressoIdlingResource.increment();
        handler.postDelayed(()->{
            Call<TvShowResponse> call = ApiConfig.getApiService().getTopRatedTvShows(API_KEY);
            call.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(@NotNull Call<TvShowResponse> call, @NotNull Response<TvShowResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            listOtherTvShow.postValue(ApiResponse.success(response.body().getResults()));
                        }
                    }else {
                        Log.e(DetailViewModel.class.getSimpleName(), "onFailure: ${response.message()}");
                    }
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()){
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TvShowResponse> call, @NotNull Throwable t) {
                    Log.e(DetailViewModel.class.getSimpleName(),t.toString());
                }
            });

            //EspressoIdlingResource.decrement();
        },TIME);
        return listOtherTvShow;
    }
}
