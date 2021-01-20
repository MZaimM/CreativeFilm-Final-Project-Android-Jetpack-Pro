package com.example.creativefilm.data.source.remote;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.LocalDataSource;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.ui.detail.DetailViewModel;
import com.example.creativefilm.ui.movies.MoviesViewModel;
import com.example.creativefilm.ui.tvshow.TvShowsViewModel;
import com.example.creativefilm.utils.AppExecutors;
import com.example.creativefilm.utils.DataDummy;
import com.example.creativefilm.utils.LiveDataTestUtil;
import com.example.creativefilm.utils.PagedListUtils;
import com.example.creativefilm.vo.Resource;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final RemoteDataSource remote = Mockito.mock(RemoteDataSource.class);
    private final LocalDataSource localDataSource = Mockito.mock(LocalDataSource.class);
    private final AppExecutors appExecutors = Mockito.mock(AppExecutors.class);

    private final FakeRepository fakeRepository = new FakeRepository(remote,localDataSource,appExecutors);

    @Mock
    private Repository repository;

    private final List<Movie> dummyMovie = DataDummy.generateDummyMovie();
    private final Integer movieId = dummyMovie.get(0).getId();
    private final List<TvShow> dummyTvShow = DataDummy.generateDummyTvShows();
    private final Integer tvShowId = dummyTvShow.get(0).getId();

    private final MovieDetail dummyMovieDetail = DataDummy.generateDummyMovieDetail().get(0);
    private final TvShowDetail dummyTvShowsDetail = DataDummy.generateDummyTvShowDetail().get(0);

    private DetailViewModel detailViewModel;


    @Test
    public void getMovie() {
        MoviesViewModel moviesViewModel = new MoviesViewModel(repository);

        DataSource.Factory<Integer, Movie> dataSourceFactory = mock(DataSource.Factory.class);

        when(localDataSource.getMovie()).thenReturn(dataSourceFactory);
        fakeRepository.getMovie(moviesViewModel.listMovie);
        Resource<PagedList<Movie>> movie = Resource.success(PagedListUtils.mockPagedList(dummyMovie));
        verify(localDataSource).getMovie();
        assertNotNull(movie.data);
        assertEquals(dummyMovie.size(), movie.data.size());

    }

    @Test
    public void getTvShows() {
        TvShowsViewModel tvShowsViewModel = new TvShowsViewModel(repository);

        DataSource.Factory<Integer, TvShow> dataSourceFactory = mock(DataSource.Factory.class);

        when(localDataSource.getTvShows()).thenReturn(dataSourceFactory);
        fakeRepository.getTvShows(tvShowsViewModel.listTvShows);
        Resource<List<TvShow>> tvShows = Resource.success(PagedListUtils.mockPagedList(dummyTvShow));
        verify(localDataSource).getTvShows();
        assertNotNull(tvShows);
        assertEquals(dummyTvShow.size(), tvShows.data.size());
    }

    @Test
    public void getMoviesDetail() {
        detailViewModel = new DetailViewModel(repository);

        MutableLiveData<MovieDetail> dummyMovieDetails = new MutableLiveData<>();
        dummyMovieDetails.setValue(dummyMovieDetail);

        when(localDataSource.getMovieDetail(movieId)).thenReturn(dummyMovieDetails);
        Resource<MovieDetail> movieDetail = LiveDataTestUtil.getValue(fakeRepository.getMoviesDetail(detailViewModel.movieDetail,movieId));
        verify(localDataSource).getMovieDetail(movieId);
        assertNotNull(movieDetail);
        assertEquals(dummyMovieDetail.getId(), movieDetail.data.getId());
        assertEquals(dummyMovieDetail.getTitle(), movieDetail.data.getTitle());
        assertEquals(dummyMovieDetail.getBackDropPath(), movieDetail.data.getBackDropPath());
        assertEquals(dummyMovieDetail.getPosterPath(), movieDetail.data.getPosterPath());
        assertEquals(dummyMovieDetail.getOverview(), movieDetail.data.getOverview());
        assertEquals(dummyMovieDetail.getReleaseDate(), movieDetail.data.getReleaseDate());
        assertEquals(dummyMovieDetail.getVoteAverage(), movieDetail.data.getVoteAverage());
    }

    @Test
    public void getTvshowDetail() {
        detailViewModel = new DetailViewModel(repository);

        MutableLiveData<TvShowDetail> dummyTvShowsDetails = new MutableLiveData<>();
        dummyTvShowsDetails.setValue(dummyTvShowsDetail);

        when(localDataSource.getTvShowDetail(tvShowId)).thenReturn(dummyTvShowsDetails);
        Resource<TvShowDetail> movieDetail = LiveDataTestUtil.getValue(fakeRepository.getTvshowDetail(detailViewModel.tvShowDetail,tvShowId));
        verify(localDataSource).getTvShowDetail(tvShowId);
        assertNotNull(movieDetail);
        assertEquals(dummyTvShowsDetail.getId(), movieDetail.data.getId());
        assertEquals(dummyTvShowsDetail.getTitle(), movieDetail.data.getTitle());
        assertEquals(dummyTvShowsDetail.getBackDropPath(), movieDetail.data.getBackDropPath());
        assertEquals(dummyTvShowsDetail.getPosterPath(), movieDetail.data.getPosterPath());
        assertEquals(dummyTvShowsDetail.getOverview(), movieDetail.data.getOverview());
        assertEquals(dummyTvShowsDetail.getReleaseDate(), movieDetail.data.getReleaseDate());
        assertEquals(dummyTvShowsDetail.getVoteAverage(), movieDetail.data.getVoteAverage());
    }

    @Test
    public void getOtherMovie() {
        detailViewModel = new DetailViewModel(repository);

        DataSource.Factory<Integer, Movie> dataSourceFactory = mock(DataSource.Factory.class);

        MutableLiveData<List<Movie>> dummyOtherMovies = new MutableLiveData<>();
        dummyOtherMovies.setValue(dummyMovie);

        when(localDataSource.getMovie()).thenReturn(dataSourceFactory);
        fakeRepository.getMovie(detailViewModel.listOtherMovie);
        Resource<List<Movie>> movie = Resource.success(PagedListUtils.mockPagedList(dummyMovie));
        verify(localDataSource).getMovie();
        assertNotNull(movie);
        assertEquals(dummyMovie.size(), movie.data.size());
    }

    @Test
    public void getOtherTvShows() {
        detailViewModel = new DetailViewModel(repository);

        DataSource.Factory<Integer, TvShow> dataSourceFactory = mock(DataSource.Factory.class);

        MutableLiveData<List<TvShow>> dummyOtherTvShows = new MutableLiveData<>();
        dummyOtherTvShows.setValue(dummyTvShow);

        when(localDataSource.getTvShows()).thenReturn(dataSourceFactory);
        fakeRepository.getTvShows(detailViewModel.listOtherTvShow);
        Resource<List<TvShow>> tvShows = Resource.success(PagedListUtils.mockPagedList(dummyTvShow));
        verify(localDataSource).getTvShows();
        assertNotNull(tvShows);
        assertEquals(dummyTvShow.size(), tvShows.data.size());
    }
}