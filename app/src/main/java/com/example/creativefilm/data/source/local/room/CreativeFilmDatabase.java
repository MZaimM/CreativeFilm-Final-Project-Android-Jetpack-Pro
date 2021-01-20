package com.example.creativefilm.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;

@Database(entities = {Movie.class, MovieDetail.class, TvShow.class, TvShowDetail.class},
        version = 1,
        exportSchema = false)
public abstract class CreativeFilmDatabase extends RoomDatabase {
    public abstract CreativeFilmDao creativeFilmDao();

    private static volatile CreativeFilmDatabase INSTANCE;

    public static CreativeFilmDatabase getInstance(Context context){
        if (INSTANCE ==null){
            synchronized (CreativeFilmDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CreativeFilmDatabase.class, "CreativeFilm.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
