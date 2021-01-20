package com.example.creativefilm.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.ui.detail.DetailActivity;

public class MoviesAdapter extends PagedListAdapter<Movie,MoviesAdapter.MovieViewHolder> {

    public MoviesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        if (movie != null){
            holder.bind(movie);
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{
        final TextView tvTitle;
        final TextView tvVote;
        final TextView tvReleaseDate;
        final TextView tvDescription;
        final ImageView imgPoster;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVote = itemView.findViewById(R.id.vote_average);
            tvReleaseDate = itemView.findViewById(R.id.tv_director);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }

        void bind(final Movie movie){
            tvTitle.setText(movie.getTitle());
            tvVote.setText(String.valueOf(movie.getVoteAverage()));
            tvReleaseDate.setText(movie.getReleaseDate());
            tvDescription.setText(movie.getOverview());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.getId());
                itemView.getContext().startActivity(intent);
            });


            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error_image))
                    .into(imgPoster);
        }
    }
}
