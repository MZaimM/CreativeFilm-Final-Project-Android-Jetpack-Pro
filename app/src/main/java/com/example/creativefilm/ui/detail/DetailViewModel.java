package com.example.creativefilm.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.vo.Resource;

import java.util.List;


public class DetailViewModel extends ViewModel {
    public  MutableLiveData<Integer> movieId = new MutableLiveData<>();
    public  MutableLiveData<Integer> tvShowId = new MutableLiveData<>();
    private  Repository repository;


    public DetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public final MutableLiveData<ApiResponse<MovieDetail>> movieDetail = new MutableLiveData<>();
    public LiveData<ApiResponse<MovieDetail>> getDetailMovie(){
        return movieDetail;
    }

    public final MutableLiveData<ApiResponse<List<Movie>>> listOtherMovie = new MutableLiveData<>();

    public final MutableLiveData<ApiResponse<TvShowDetail>> tvShowDetail = new MutableLiveData<>();
    public LiveData<ApiResponse<TvShowDetail>> getDetailTvShow(){
        return tvShowDetail;
    }

    public final MutableLiveData<ApiResponse<List<TvShow>>> listOtherTvShow = new MutableLiveData<>();

    public void setSelectedMovie(Integer movieId){
        this.movieId.setValue(movieId);
    }

    public void setSelectedTvShow(Integer tvShowId){
        this.tvShowId.setValue(tvShowId);
    }

    public final LiveData<Resource<MovieDetail>> getMovieDetail = Transformations.switchMap(movieId,
            mCourseId -> repository.getMoviesDetail(movieDetail, mCourseId));

    public final LiveData<Resource<TvShowDetail>> getTvShowsDetail = Transformations.switchMap(tvShowId,
            mTvShowId->repository.getTvshowDetail(tvShowDetail, mTvShowId));

    public final LiveData<Resource<PagedList<Movie>>> getOtherMovie = Transformations.switchMap(movieId,
            mMovieId->repository.getOtherMovie(listOtherMovie));

    public final LiveData<Resource<PagedList<TvShow>>> getOtherTvShows= Transformations.switchMap(tvShowId,
            mTvShowId->repository.getOtherTvShows(listOtherTvShow));

    public void setFavoriteMovie(){
        Resource<MovieDetail> movie = getMovieDetail.getValue();
        if (movie != null){
            MovieDetail movieDetail = movie.data;
            boolean newstate = !movieDetail.isFavorite();
                repository.setMovieFavorite(movieDetail, newstate);
        }
    }

    public void setFavoriteTvShow(){
       Resource<TvShowDetail> tvShow = getTvShowsDetail.getValue();
       if (tvShow !=null){
           TvShowDetail tvShowDetail = tvShow.data;
           boolean newstate = !tvShowDetail.isFavorite();
               repository.setTvShowFavorite(tvShowDetail, newstate);
       }

    }
}
