package com.example.creativefilm.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.utils.JsonHelper;

import java.util.List;

public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;
    private final JsonHelper jsonHelper;


    public RemoteDataSource(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public static RemoteDataSource getInstance(JsonHelper helper){
        if (INSTANCE == null){
            INSTANCE = new RemoteDataSource(helper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<Movie>>> getMovie(MutableLiveData<ApiResponse<List<Movie>>> listMovie){
        return jsonHelper.getMovie(listMovie);
    }

    public LiveData<ApiResponse<List<TvShow>>> getTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listTvShows){
        return jsonHelper.getTvShows(listTvShows);
    }

    public LiveData<ApiResponse<MovieDetail>> getMoviesDetail(MutableLiveData<ApiResponse<MovieDetail>> movieDetail, Integer movieId){
        return jsonHelper.getMoviesDetail(movieDetail,movieId);
    }

    public LiveData<ApiResponse<TvShowDetail>> getTvshowDetail(MutableLiveData<ApiResponse<TvShowDetail>> tvShowDetail, Integer tvShowId){
        return jsonHelper.getTvShowsDetail(tvShowDetail,tvShowId);
    }

    public LiveData<ApiResponse<List<Movie>>> getOtherMovie(MutableLiveData<ApiResponse<List<Movie>>> listOtherMovie){
        return jsonHelper.getOtherMovie(listOtherMovie);
    }

    public LiveData<ApiResponse<List<TvShow>>> getOtherTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listOtherTvShow){
        return jsonHelper.getOtherTvShows(listOtherTvShow);
    }

}

