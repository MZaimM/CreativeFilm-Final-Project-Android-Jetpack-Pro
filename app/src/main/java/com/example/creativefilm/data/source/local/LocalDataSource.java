package com.example.creativefilm.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.local.room.CreativeFilmDao;

import java.util.List;

public class LocalDataSource {

    private static LocalDataSource INSTANCE;
    private final CreativeFilmDao creativeFilmDao;

    public LocalDataSource(CreativeFilmDao creativeFilmDao) {
        this.creativeFilmDao = creativeFilmDao;
    }

    public static LocalDataSource getInstance(CreativeFilmDao creativeFilmDao){
        if (INSTANCE == null){
            INSTANCE = new LocalDataSource(creativeFilmDao);
        }
        return INSTANCE;
    }

    public DataSource.Factory<Integer, Movie> getMovie(){
        return creativeFilmDao.getMovie();
    }

    public DataSource.Factory<Integer, TvShow> getTvShows(){
        return creativeFilmDao.getTvShow();
    }

    public LiveData<MovieDetail> getMovieDetail(Integer id){
        return creativeFilmDao.getMovieDetail(id);
    }

    public LiveData<TvShowDetail> getTvShowDetail(Integer id){
        return creativeFilmDao.getTvShowDetail(id);
    }

    public void insertMovies(List<Movie> movies){
        creativeFilmDao.insertMovies(movies);
    }


    public void insertTvShows(List<TvShow> tvShows){
        creativeFilmDao.insertTvShow(tvShows);
    }

    public void insertMovieDetail(MovieDetail movie){
        creativeFilmDao.insertMovieDetail(movie);
    }

    public void insertTvShowDetail(TvShowDetail tvShowDetail){
        creativeFilmDao.insertTvShowDetail(tvShowDetail);
    }

    public void setFavoriteMovie(MovieDetail movie, Boolean state){
        movie.setFavorite(state);
        creativeFilmDao.updateMovie(movie);
    }

    public void setFavoriteTvShow(TvShowDetail tvShow, Boolean state){
        tvShow.setFavorite(state);
        creativeFilmDao.updateTvShow(tvShow);
    }

    public DataSource.Factory<Integer, MovieDetail> setFavoriteMovie(){
        return creativeFilmDao.getFavoriteMovie();
    }

    public DataSource.Factory<Integer, TvShowDetail> setFavoriteTvShow(){
        return creativeFilmDao.getFavoriteTvShow();
    }

    public DataSource.Factory<Integer, Movie> getNewestMovie(){
        return creativeFilmDao.getNewestMovie();
    }

    public DataSource.Factory<Integer, TvShow> getNewestTvShow(){
        return creativeFilmDao.getNewestTvShow();
    }


}
