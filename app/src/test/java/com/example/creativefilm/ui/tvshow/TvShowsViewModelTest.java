package com.example.creativefilm.ui.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TvShowsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Repository repository;

    @Mock
    private TvShowsViewModel viewModel;

    @Mock
    private Observer<Resource<PagedList<TvShow>>> observer;

    @Mock
    private PagedList<TvShow> pagedList;

    @Before
    public void setUp(){
        viewModel = new TvShowsViewModel(repository);
    }

    @Test
    public void getTvShows() {
        Resource<PagedList<TvShow>> dummyTvShow = Resource.success(pagedList);
        when(dummyTvShow.data.size()).thenReturn(20);

        MutableLiveData<Resource<PagedList<TvShow>>> dummyTvShows = new MutableLiveData<>();
        dummyTvShows.setValue(dummyTvShow);

        when(repository.getTvShows(viewModel.listTvShows)).thenReturn(dummyTvShows);
        List<TvShow> tvShowsList = viewModel.getTvShows().getValue().data;
        verify(repository).getTvShows(viewModel.listTvShows);
        assertNotNull(tvShowsList);
        assertEquals(20, tvShowsList.size());

        viewModel.getTvShows().observeForever(observer);
        verify(observer).onChanged(dummyTvShow);
    }
}