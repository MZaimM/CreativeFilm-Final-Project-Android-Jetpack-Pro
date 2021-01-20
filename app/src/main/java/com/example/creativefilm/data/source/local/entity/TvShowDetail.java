package com.example.creativefilm.data.source.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tvshowdetail",
        primaryKeys = "id",
        foreignKeys = @ForeignKey(entity = TvShow.class,
            parentColumns = "id",
            childColumns = "id"),
        indices = @Index(value = "id"))
public class TvShowDetail {



    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private final String posterPath;

    @ColumnInfo(name = "title")
    @SerializedName("name")
    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("first_air_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private final Double voteAverage;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private final String overview;

    /*@ColumnInfo(name = "genres")
    @SerializedName("genres")
    private List<Genres> genres;*/

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private final String backDropPath;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @ColumnInfo(name = "favorite")
    private boolean favorite= false;

    public TvShowDetail(Integer id,String title, String posterPath,  String releaseDate, String overview,Double voteAverage, String backDropPath, Boolean favorite) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.backDropPath = backDropPath;
        if (favorite != null){
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

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    /*public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }*/

    public String getBackDropPath() {
        return backDropPath;
    }

}
