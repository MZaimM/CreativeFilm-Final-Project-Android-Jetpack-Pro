package com.example.creativefilm.ui.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.data.source.remote.response.ApiResponse;
import com.example.creativefilm.ui.adapter.OtherMovieAdapter;
import com.example.creativefilm.ui.adapter.OtherTvShowAdapter;
import com.example.creativefilm.utils.viewmodel.ViewModelFactory;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,DetailCallback {

    private DetailViewModel viewModel;
    private TextView tvTitleDetail;
    private TextView tvVoteAverageDetail;
    private TextView tvReleasedate;
    private TextView tvDescriptionDetail;
    private ImageView imgPosterDetail;
    private ImageView imgBackDrop;
    public static final String EXTRA_MOVIE = "1";
    public static final String EXTRA_TVSHOW = "2";
    private final List<Movie> list = new ArrayList<>();
    private final List<TvShow> list1 = new ArrayList<>();
    private ProgressBar progressBar;
    private FloatingActionButton fabDetail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTitleDetail = findViewById(R.id.tv_title_detail);
        tvVoteAverageDetail = findViewById(R.id.tv_vote_avarage);
        tvReleasedate = findViewById(R.id.tv_release_date_detail);
        tvDescriptionDetail = findViewById(R.id.tv_description_detail);
        imgPosterDetail = findViewById(R.id.image_detail);

        imgBackDrop = findViewById(R.id.image_back_drop);
        Button btnShare = findViewById(R.id.btn_share);
        TextView tvOther = findViewById(R.id.tvOther);
        progressBar = findViewById(R.id.progress_bar);
        fabDetail = findViewById(R.id.fab_detail);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory).get(DetailViewModel.class);

        OtherMovieAdapter movieAdapter = new OtherMovieAdapter(list);
        OtherTvShowAdapter tvShowAdapter = new OtherTvShowAdapter(list1);

        RecyclerView rvOtherMovies = findViewById(R.id.rvOtherMovie);
        rvOtherMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true));
        rvOtherMovies.setHasFixedSize(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            Integer movieId = extras.getInt(EXTRA_MOVIE);
            Integer tvShowId = extras.getInt(EXTRA_TVSHOW);
            if (movieId != null){
                viewModel.setSelectedMovie(movieId);

                viewModel.getMovieDetail.observe(this, movie -> {
                    if (movie != null){
                        switch (movie.status){
                            case LOADING:
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                progressBar.setVisibility(View.VISIBLE);
                                if (movie.data != null){

                                    populateMovie(movie.data);
                                    rvOtherMovies.setAdapter(movieAdapter);
                                    boolean state = movie.data.isFavorite();
                                    setStatusFavorite(state);
                                    fabDetail.setOnClickListener(v -> viewModel.setFavoriteMovie());
                                    progressBar.setVisibility(View.GONE);
                                }
                                break;
                            case ERROR:
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

                viewModel.getOtherMovie.observe(this, movies -> {
                    if (movies != null){
                        switch (movies.status){
                            case LOADING:
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                progressBar.setVisibility(View.GONE);
                                movieAdapter.setOtherMovie(movies.data);
                                movieAdapter.notifyDataSetChanged();
                                break;
                            case ERROR:
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

            if (tvShowId != null){
                viewModel.setSelectedTvShow(tvShowId);
                viewModel.getTvShowsDetail.observe(this, tvShowDetail -> {
                    if (tvShowDetail != null){
                        switch (tvShowDetail.status){
                            case LOADING:
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                progressBar.setVisibility(View.VISIBLE);
                                if (tvShowDetail.data != null){

                                    populateTvShows(tvShowDetail.data);
                                    rvOtherMovies.setAdapter(tvShowAdapter);
                                    boolean state = tvShowDetail.data.isFavorite();
                                    setStatusFavorite(state);
                                    fabDetail.setOnClickListener(v -> viewModel.setFavoriteTvShow());
                                    tvOther.setText(R.string.otherTvShows);
                                    progressBar.setVisibility(View.GONE);
                                }
                                break;
                            case ERROR:
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

                viewModel.getOtherTvShows.observe(this, tvShows -> {
                    if (tvShows != null){
                        switch (tvShows.status){
                            case LOADING:
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                progressBar.setVisibility(View.GONE);
                                tvShowAdapter.setOtherTvShow(tvShows.data);
                                tvShowAdapter.notifyDataSetChanged();
                                break;
                            case ERROR:
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        }

        btnShare.setOnClickListener(this);

    }

    private void populateMovie(final MovieDetail movie){
        progressBar.setVisibility(View.VISIBLE);
        tvTitleDetail.setText(movie.getTitle());
        tvVoteAverageDetail.setText(String.valueOf(movie.getVoteAverage()));
        tvReleasedate.setText(movie.getReleaseDate());
        tvDescriptionDetail.setText(movie.getOverview());

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+movie.getBackDropPath())
                .into(imgBackDrop);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+movie.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error_image))
                .into(imgPosterDetail);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void populateTvShows(TvShowDetail tvShow){
        progressBar.setVisibility(View.VISIBLE);
        tvTitleDetail.setText(tvShow.getTitle());
        tvVoteAverageDetail.setText(String.valueOf(tvShow.getVoteAverage()));
        tvReleasedate.setText(tvShow.getReleaseDate());
        tvDescriptionDetail.setText(tvShow.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+tvShow.getBackDropPath())
                .into(imgBackDrop);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+tvShow.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error_image))
                .into(imgPosterDetail);
        progressBar.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_share) {
            viewModel.getDetailMovie().observe(this, this::onShareClick1);
            viewModel.getDetailTvShow().observe(this, this::onShareClick);
        }
    }

    @Override
    public void onShareClick1(ApiResponse<MovieDetail> movie) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang")
                .setText(getResources().getString(R.string.share_text,movie.body.getTitle()))
                .startChooser();
    }


    @Override
    public void onShareClick(ApiResponse<TvShowDetail> tvShow) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang")
                .setText(getResources().getString(R.string.share_text,tvShow.body.getTitle()))
                .startChooser();
    }

    public void setStatusFavorite(Boolean statusFavorite){
        if (statusFavorite){
            fabDetail.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else {
            fabDetail.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }


}