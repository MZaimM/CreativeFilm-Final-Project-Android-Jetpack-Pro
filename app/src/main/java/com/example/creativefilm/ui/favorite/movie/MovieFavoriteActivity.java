package com.example.creativefilm.ui.favorite.movie;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.ui.movies.MoviesViewModel;
import com.example.creativefilm.utils.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class MovieFavoriteActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private MoviesViewModel viewModel;
    private MovieFavoriteAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Movies Favorite");
        }

        RecyclerView rvMoviesFav = findViewById(R.id.rvMoviesFav);
        progressBar = findViewById(R.id.progress_bar);
        itemTouchHelper.attachToRecyclerView(rvMoviesFav);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this, factory).get(MoviesViewModel.class);

        moviesAdapter = new MovieFavoriteAdapter();

        progressBar.setVisibility(View.VISIBLE);

        viewModel.getMovieFavorite().observe(this, movieDetails -> {
            progressBar.setVisibility(View.GONE);
            moviesAdapter.submitList(movieDetails);
            moviesAdapter.notifyDataSetChanged();
        });

        rvMoviesFav.setHasFixedSize(true);
        rvMoviesFav.setLayoutManager(new LinearLayoutManager(this));
        rvMoviesFav.setAdapter(moviesAdapter);

    }

    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (viewHolder.itemView.getRootView() != null) {
                int swipedPosition = viewHolder.getAdapterPosition();
                MovieDetail movieDetail = moviesAdapter.getSwipedData(swipedPosition);
                viewModel.setMovieFav(movieDetail);
                Snackbar snackbar = Snackbar.make(viewHolder.itemView.getRootView(), R.string.message_undo, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.ok, v -> viewModel.setMovieFav(movieDetail));
                snackbar.show();
            }
        }
    });
}