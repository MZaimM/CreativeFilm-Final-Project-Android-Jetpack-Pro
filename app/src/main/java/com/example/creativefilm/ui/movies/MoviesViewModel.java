package com.example.creativefilm.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.vo.Resource;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    public final MutableLiveData<ApiResponse<List<Movie>>> listMovie = new MutableLiveData<>();


    private final Repository repository;

    public MoviesViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<Movie>>> getMovie(){
        return repository.getMovie(listMovie);

    }

    public LiveData<PagedList<MovieDetail>> getMovieFavorite(){
        return repository.getMovieFavorite();
    }

    public LiveData<PagedList<Movie>> getNewestMovie(){
        return repository.getNewestMovie();
    }

    public void setMovieFav(MovieDetail movieDetail) {
        final boolean newState = !movieDetail.isFavorite();
        repository.setMovieFavorite(movieDetail,newState);
    }


}
