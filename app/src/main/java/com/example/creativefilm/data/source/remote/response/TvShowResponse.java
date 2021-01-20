package com.example.creativefilm.data.source.remote.response;

import com.example.creativefilm.data.source.local.entity.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("results")
    private final List<TvShow> results;

    public TvShowResponse(List<TvShow> results) {
        this.results = results;
    }

    public List<TvShow> getResults() {
        return results;
    }


}
