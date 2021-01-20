package com.example.creativefilm.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.local.LocalDataSource;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.remote.RemoteDataSource;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.utils.AppExecutors;
import com.example.creativefilm.vo.Resource;

import java.util.List;

public class Repository implements DataSource {
    private volatile static Repository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;
    public final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    public Repository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    public static Repository getInstance(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (Repository.class){
                if (INSTANCE == null){
                    INSTANCE = new Repository(remoteDataSource,localDataSource, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<PagedList<Movie>>> getMovie(MutableLiveData<ApiResponse<List<Movie>>> listMovie) {
        return new NetworkBoundResource<PagedList<Movie>, List<Movie>>(appExecutors) {
            @Override
            protected LiveData<PagedList<Movie>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getMovie(),config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<Movie> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return remoteDataSource.getMovie(listMovie);
            }

            @Override
            protected void saveCallResult(List<Movie> data) {
                localDataSource.insertMovies(data);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShow>>> getTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listTvShows) {
        return new NetworkBoundResource<PagedList<TvShow>, List<TvShow>>(appExecutors) {
            @Override
            protected LiveData<PagedList<TvShow>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getTvShows(),config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShow> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvShow>>> createCall() {
                return remoteDataSource.getTvShows(listTvShows);
            }

            @Override
            protected void saveCallResult(List<TvShow> data) {
                localDataSource.insertTvShows(data);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieDetail>> getMoviesDetail(MutableLiveData<ApiResponse<MovieDetail>> movieDetail, Integer movieId) {
        return new NetworkBoundResource<MovieDetail, MovieDetail>(appExecutors) {
            @Override
            protected LiveData<MovieDetail> loadFromDB() {
                return localDataSource.getMovieDetail(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieDetail data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<MovieDetail>> createCall() {
                return remoteDataSource.getMoviesDetail(movieDetail, movieId);
            }

            @Override
            protected void saveCallResult(MovieDetail data) {
                localDataSource.insertMovieDetail(data);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvShowDetail>> getTvshowDetail(MutableLiveData<ApiResponse<TvShowDetail>> tvShowDetail, Integer tvShowId) {
        return new NetworkBoundResource<TvShowDetail, TvShowDetail>(appExecutors) {
            @Override
            protected LiveData<TvShowDetail> loadFromDB() {
                return localDataSource.getTvShowDetail(tvShowId);
            }

            @Override
            protected Boolean shouldFetch(TvShowDetail data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<TvShowDetail>> createCall() {
                return remoteDataSource.getTvshowDetail(tvShowDetail, tvShowId);
            }

            @Override
            protected void saveCallResult(TvShowDetail data) {
                localDataSource.insertTvShowDetail(data);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<Movie>>> getOtherMovie(MutableLiveData<ApiResponse<List<Movie>>> listOtherMovie) {
        return new NetworkBoundResource<PagedList<Movie>, List<Movie>>(appExecutors) {
            @Override
            protected LiveData<PagedList<Movie>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getMovie(), config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<Movie> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return remoteDataSource.getOtherMovie(listOtherMovie);
            }

            @Override
            protected void saveCallResult(List<Movie> data) {
                localDataSource.insertMovies(data);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShow>>> getOtherTvShows(MutableLiveData<ApiResponse<List<TvShow>>> listOtherTvShow) {
        return new NetworkBoundResource<PagedList<TvShow>, List<TvShow>>(appExecutors) {
            @Override
            protected LiveData<PagedList<TvShow>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20)
                        .build();
                return new LivePagedListBuilder<>(localDataSource.getTvShows(), config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShow> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvShow>>> createCall() {
                return remoteDataSource.getOtherTvShows(listOtherTvShow);
            }

            @Override
            protected void saveCallResult(List<TvShow> data) {
                localDataSource.insertTvShows(data);
            }
        }.asLiveData();
    }

    @Override
    public void setMovieFavorite(MovieDetail movieFavorite, boolean state) {
        appExecutors.diskIO().execute(()-> localDataSource.setFavoriteMovie(movieFavorite, state));
    }

    @Override
    public void setTvShowFavorite(TvShowDetail tvShowFavorite, boolean state) {
        appExecutors.diskIO().execute(()->localDataSource.setFavoriteTvShow(tvShowFavorite,state));
    }

    @Override
    public LiveData<PagedList<MovieDetail>> getMovieFavorite() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
        return new LivePagedListBuilder<>(localDataSource.setFavoriteMovie(), config).build();
    }

    @Override
    public LiveData<PagedList<TvShowDetail>> getTvShowFavorite() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
        return new LivePagedListBuilder<>(localDataSource.setFavoriteTvShow(),config).build();
    }

    @Override
    public LiveData<PagedList<Movie>> getNewestMovie() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getNewestMovie(), config).build();
    }

    @Override
    public LiveData<PagedList<TvShow>> getNewestTvShow() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getNewestTvShow(),config).build();
    }
}
