package com.example.creativefilm.ui.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.Movie;
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
public class MoviesViewModelTest {
    private MoviesViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Repository repository;

    @Mock
    private Observer<Resource<PagedList<Movie>>> observer;

    @Mock
    private PagedList<Movie> pagedList;

    @Before
    public void setUp(){
        viewModel = new MoviesViewModel(repository);
    }

    @Test
    public void getMovie() {
        Resource<PagedList<Movie>> dummyMovie = Resource.success(pagedList);
        when(dummyMovie.data.size()).thenReturn(20);

        MutableLiveData<Resource<PagedList<Movie>>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(dummyMovie);

        when(repository.getMovie(viewModel.listMovie)).thenReturn(dummyMovies);
        Resource<PagedList<Movie>> moviesList = LiveDataTestUtil.getValue(viewModel.getMovie());
        verify(repository).getMovie(viewModel.listMovie);

        assertNotNull(moviesList);
        assertEquals(20, moviesList.data.size());

        viewModel.getMovie().observeForever(observer);
        verify(observer).onChanged(dummyMovie);
    }
}