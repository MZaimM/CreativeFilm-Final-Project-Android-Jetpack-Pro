package com.example.creativefilm.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;

import java.util.List;

@Dao
public interface CreativeFilmDao {

    @Query("SELECT*FROM movie ORDER BY vote_average DESC")
    DataSource.Factory<Integer,Movie> getMovie();

    @Query("SELECT*FROM moviedetail WHERE id = :id")
    LiveData<MovieDetail> getMovieDetail(Integer id);

    @Update
    void updateMovie(MovieDetail movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieDetail(MovieDetail movieDetail);

    @Query("SELECT*FROM tvshow ORDER BY vote_average DESC")
    DataSource.Factory<Integer, TvShow> getTvShow();

    @Query("SELECT*FROM tvshowdetail WHERE id = :id")
    LiveData<TvShowDetail> getTvShowDetail(Integer id);

    @Update
    void updateTvShow(TvShowDetail tvShow);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(List<TvShow> tvShows);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShowDetail(TvShowDetail tvShowDetail);

    @Query("SELECT*FROM moviedetail WHERE favorite = 1")
    DataSource.Factory<Integer, MovieDetail>getFavoriteMovie();

    @Query("SELECT*FROM tvshowdetail WHERE favorite = 1")
    DataSource.Factory<Integer, TvShowDetail> getFavoriteTvShow();

    @Query("SELECT*FROM movie ORDER BY release_date DESC")
    DataSource.Factory<Integer,Movie> getNewestMovie();

    @Query("SELECT*FROM tvshow ORDER BY release_date DESC")
    DataSource.Factory<Integer, TvShow> getNewestTvShow();

    @Update
    void updateListMovie(Movie movie);

    @Update
    void updateListTvShow(TvShow tvShow);

}
