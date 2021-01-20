package com.example.creativefilm.di;

import android.content.Context;

import com.example.creativefilm.data.source.Repository;
import com.example.creativefilm.data.source.local.LocalDataSource;
import com.example.creativefilm.data.source.local.room.CreativeFilmDatabase;
import com.example.creativefilm.data.source.remote.RemoteDataSource;
import com.example.creativefilm.utils.AppExecutors;
import com.example.creativefilm.utils.JsonHelper;

public class Injection {
    public static Repository provideRepository(Context context){
        CreativeFilmDatabase database = CreativeFilmDatabase.getInstance(context);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper());
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.creativeFilmDao());
        AppExecutors appExecutors = new AppExecutors();

        return Repository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }
}
