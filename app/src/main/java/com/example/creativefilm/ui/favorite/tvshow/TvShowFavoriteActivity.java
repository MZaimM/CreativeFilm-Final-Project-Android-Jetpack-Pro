package com.example.creativefilm.ui.favorite.tvshow;

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
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.ui.tvshow.TvShowsViewModel;
import com.example.creativefilm.utils.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class TvShowFavoriteActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TvShowsViewModel viewModel;
    private TvShowFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Tv Show Favorite");
        }

        RecyclerView rvTvShows = findViewById(R.id.rvTvShowsFav);
        progressBar = findViewById(R.id.progress_bar);
        itemTouchHelper.attachToRecyclerView(rvTvShows);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this,factory).get(TvShowsViewModel.class);

        adapter = new TvShowFavoriteAdapter();

        progressBar.setVisibility(View.VISIBLE);

        rvTvShows.setHasFixedSize(true);
        rvTvShows.setLayoutManager(new LinearLayoutManager(this));
        rvTvShows.setAdapter(adapter);

        viewModel.getTvShowFavorite().observe(this, tvShowDetails -> {
            progressBar.setVisibility(View.GONE);
            adapter.submitList(tvShowDetails);
            adapter.notifyDataSetChanged();
        });
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
                TvShowDetail tvShowDetail = adapter.getSwipedData(swipedPosition);
                viewModel.setMovieFav(tvShowDetail);
                Snackbar snackbar = Snackbar.make(viewHolder.itemView.getRootView(), R.string.message_undo, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.ok, v -> viewModel.setMovieFav(tvShowDetail));
                snackbar.show();
            }
        }
    });
}