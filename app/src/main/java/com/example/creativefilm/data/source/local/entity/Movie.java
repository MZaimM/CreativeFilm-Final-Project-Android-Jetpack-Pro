package com.example.creativefilm.data.source.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey
    @ColumnInfo (name = "id")
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private final String posterPath;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private final Double voteAverage;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private final String overview;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @ColumnInfo(name = "favorite")
    private boolean favorite= false;

    public Movie(Integer id, String title, String overview, String releaseDate,String posterPath, Double voteAverage, Boolean favorite) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        if (favorite !=null){
            this.favorite = favorite;
        }

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

}
