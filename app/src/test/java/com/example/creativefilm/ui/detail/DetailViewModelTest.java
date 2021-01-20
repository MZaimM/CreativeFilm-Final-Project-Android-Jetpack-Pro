package com.example.creativefilm.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.utils.DataDummy;
import com.example.creativefilm.utils.LiveDataTestUtil;
import com.example.creativefilm.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Repository repository;

    @Mock
    private Observer<Resource<MovieDetail>> movieObserver;

    @Mock
    private Observer<Resource<PagedList<Movie>>> movieOtherObserver;

    @Mock
    private Observer<Resource<TvShowDetail>> tvShowObserver;

    @Mock
    private Observer<Resource<PagedList<TvShow>>> tvShowOtherObserver;

    @Mock
    private PagedList<Movie> pagedListMovie;

    @Mock
    private PagedList<TvShow> pagedListTvMovie;


    private DetailViewModel viewModel;
    private final Movie dummyMovie = DataDummy.generateDummyMovie().get(0);
    private final TvShow dummyTvShow = DataDummy.generateDummyTvShows().get(0);
    private final Integer movieId = dummyMovie.getId();
    private final Integer tvShowId = dummyTvShow.getId();
    private final MovieDetail dummyMovieDetail = DataDummy.generateDummyMovieDetail().get(0);
    private final TvShowDetail dummyTvShowsDetail = DataDummy.generateDummyTvShowDetail().get(0);


    @Before
    public void setUp(){
         viewModel = new DetailViewModel(repository);
         viewModel.setSelectedMovie(movieId);
         viewModel.setSelectedTvShow(tvShowId);

    }

    @Test
    public void getMovies() {
        Resource<MovieDetail> dummyMovieDetails = Resource.success(dummyMovieDetail);
        MutableLiveData<Resource<MovieDetail>> movie = new MutableLiveData<>();
        movie.setValue(dummyMovieDetails);

        when(repository.getMoviesDetail(viewModel.movieDetail,movieId)).thenReturn(movie);

        Resource<MovieDetail> movieDetail = LiveDataTestUtil.getValue(viewModel.getMovieDetail);
        verify(repository).getMoviesDetail(viewModel.movieDetail,movieId);
        assertNotNull(movieDetail);
        assertEquals(dummyMovieDetail.getId(), movieDetail.data.getId());
        assertEquals(dummyMovieDetail.getTitle(), movieDetail.data.getTitle());
        assertEquals(dummyMovieDetail.getOverview(), movieDetail.data.getOverview());
        assertEquals(dummyMovieDetail.getPosterPath(), movieDetail.data.getPosterPath());
        assertEquals(dummyMovieDetail.getReleaseDate(), movieDetail.data.getReleaseDate());
        assertEquals(dummyMovieDetail.getVoteAverage(), movieDetail.data.getVoteAverage());

        viewModel.getMovieDetail.observeForever(movieObserver);
        verify(movieObserver).onChanged(dummyMovieDetails);
    }

    @Test
    public void getOtherMovie() {
        Resource<PagedList<Movie>> dummyMovie = Resource.success(pagedListMovie);
        when(dummyMovie.data.size()).thenReturn(20);

        MutableLiveData<Resource<PagedList<Movie>>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(dummyMovie);

        when(repository.getOtherMovie(viewModel.listOtherMovie)).thenReturn(dummyMovies);
        Resource<PagedList<Movie>> moviesList = LiveDataTestUtil.getValue(viewModel.getOtherMovie);
        verify(repository).getOtherMovie(viewModel.listOtherMovie);

        assertNotNull(moviesList);
        assertEquals(20, moviesList.data.size());

        viewModel.getOtherMovie.observeForever(movieOtherObserver);
        verify(movieOtherObserver).onChanged(dummyMovie);
    }

    @Test
    public void getTvShows() {
        Resource<TvShowDetail> dummyTvShowDetails = Resource.success(dummyTvShowsDetail);
        MutableLiveData<Resource<TvShowDetail>> tvShow = new MutableLiveData<>();
        tvShow.setValue(dummyTvShowDetails);

        when(repository.getTvshowDetail(viewModel.tvShowDetail,tvShowId)).thenReturn(tvShow);

        Resource<TvShowDetail> tvShowDetail = LiveDataTestUtil.getValue(viewModel.getTvShowsDetail);
        verify(repository).getTvshowDetail(viewModel.tvShowDetail,tvShowId);

        assertNotNull(tvShowDetail);
        assertEquals(dummyTvShowsDetail.getId(), tvShowDetail.data.getId());
        assertEquals(dummyTvShowsDetail.getTitle(), tvShowDetail.data.getTitle());
        assertEquals(dummyTvShowsDetail.getOverview(), tvShowDetail.data.getOverview());
        assertEquals(dummyTvShowsDetail.getPosterPath(), tvShowDetail.data.getPosterPath());
        assertEquals(dummyTvShowsDetail.getReleaseDate(), tvShowDetail.data.getReleaseDate());
        assertEquals(dummyTvShowsDetail.getVoteAverage(), tvShowDetail.data.getVoteAverage());

        viewModel.getTvShowsDetail.observeForever(tvShowObserver);
        verify(tvShowObserver).onChanged(dummyTvShowDetails);
    }

    @Test
    public void getOtherTvShows() {
        Resource<PagedList<TvShow>> dummyTvShow = Resource.success(pagedListTvMovie);
        when(dummyTvShow.data.size()).thenReturn(20);

        MutableLiveData<Resource<PagedList<TvShow>>> dummyTvShows = new MutableLiveData<>();
        dummyTvShows.setValue(dummyTvShow);

        when(repository.getOtherTvShows(viewModel.listOtherTvShow)).thenReturn(dummyTvShows);
        Resource<PagedList<TvShow>> tvShowsList = LiveDataTestUtil.getValue(viewModel.getOtherTvShows);
        verify(repository).getOtherTvShows(viewModel.listOtherTvShow);
        assertNotNull(tvShowsList);
        assertEquals(20, tvShowsList.data.size());

        viewModel.getOtherTvShows.observeForever(tvShowOtherObserver);
        verify(tvShowOtherObserver).onChanged(dummyTvShow);
    }
}