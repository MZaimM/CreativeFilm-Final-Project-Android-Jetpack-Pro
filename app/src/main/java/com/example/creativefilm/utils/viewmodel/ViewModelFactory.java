package com.example.creativefilm.utils.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.di.Injection;
import com.example.creativefilm.ui.detail.DetailViewModel;
import com.example.creativefilm.ui.movies.MoviesViewModel;
import com.example.creativefilm.ui.tvshow.TvShowsViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    public static ViewModelFactory getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if (INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(MoviesViewModel.class)){
            return (T) new MoviesViewModel(repository);
        }else if (modelClass.isAssignableFrom(TvShowsViewModel.class)){
            return (T) new TvShowsViewModel(repository);
        }else if (modelClass.isAssignableFrom(DetailViewModel.class)){
            return (T) new DetailViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
