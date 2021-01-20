package com.example.creativefilm.ui.detail;

import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;

public interface DetailCallback {
    void onShareClick1(ApiResponse<MovieDetail> movie);
    void onShareClick(ApiResponse<TvShowDetail> tvShow);
}
