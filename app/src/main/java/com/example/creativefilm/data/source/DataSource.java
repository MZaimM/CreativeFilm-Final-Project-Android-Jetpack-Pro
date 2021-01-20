package com.example.creativefilm.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.vo.Resource;

import java.util.List;

public interface DataSource {

    LiveData<Resource<PagedList<Movie>>> getMovie(MutableLiveData<ApiResponse<List<Movie>>> listMovie);
    LiveData<Resource<PagedList<TvShow>>> getTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listTvShows);
    LiveData<Resource<MovieDetail>> getMoviesDetail(MutableLiveData<ApiResponse<MovieDetail>> movieDetail, Integer movieId);
    LiveData<Resource<TvShowDetail>> getTvshowDetail(MutableLiveData<ApiResponse<TvShowDetail>> tvShowDetail, Integer tvShowId);
    LiveData<Resource<PagedList<Movie>>> getOtherMovie(MutableLiveData<ApiResponse<List<Movie>>> listOtherMovie);
    LiveData<Resource<PagedList<TvShow>>> getOtherTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listOtherTvShow);

    void setMovieFavorite(MovieDetail movieFavorite, boolean state);
    void setTvShowFavorite(TvShowDetail tvShowFavorite, boolean state);

    LiveData<PagedList<MovieDetail>> getMovieFavorite();
    LiveData<PagedList<TvShowDetail>> getTvShowFavorite();

    LiveData<PagedList<Movie>> getNewestMovie();
    LiveData<PagedList<TvShow>> getNewestTvShow();



}
