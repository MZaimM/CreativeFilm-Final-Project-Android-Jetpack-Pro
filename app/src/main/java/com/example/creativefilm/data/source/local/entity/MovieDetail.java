package com.example.creativefilm.data.source.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "moviedetail",
        primaryKeys = "id" ,
        foreignKeys = @ForeignKey(entity = Movie.class,
                parentColumns = "id",
                childColumns = "id"),
        indices = @Index(value = "id"))
public class MovieDetail {



    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private final String backDropPath;


    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    /*@SerializedName("genres")
    private List<Genres> genres;*/

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private final String posterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

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

    public MovieDetail( Integer id, String title,  String posterPath, String releaseDate, String overview, Double voteAverage, String backDropPath,Boolean favorite) {
        this.backDropPath = backDropPath;
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        if (favorite != null){
            this.favorite = favorite;
        }
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private final Double voteAverage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }*/

    public String getPosterPath() {
        return posterPath;
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

    public String getBackDropPath() {
        return backDropPath;
    }

}
