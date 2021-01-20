package com.example.creativefilm.ui.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.vo.Resource;

import java.util.List;


public class TvShowsViewModel extends ViewModel {
    public final MutableLiveData<ApiResponse<List<TvShow>>> listTvShows = new MutableLiveData<>();

    private final Repository repository;

    public TvShowsViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<TvShow>>> getTvShows(){
        return repository.getTvShows(listTvShows);
    }

    public LiveData<PagedList<TvShowDetail>> getTvShowFavorite(){
        return repository.getTvShowFavorite();
    }

    public LiveData<PagedList<TvShow>> getNewestTvShow(){
        return repository.getNewestTvShow();
    }

    public void setMovieFav(TvShowDetail tvShowDetail) {
        final boolean newState = !tvShowDetail.isFavorite();
        repository.setTvShowFavorite(tvShowDetail,newState);
    }
}
