package com.example.creativefilm.ui.favorite.movie;

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
import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.ui.detail.DetailActivity;

public class MovieFavoriteAdapter extends PagedListAdapter<MovieDetail, MovieFavoriteAdapter.MoieFavoriteViewHolder> {

    protected MovieFavoriteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<MovieDetail> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieDetail>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieDetail oldItem, @NonNull MovieDetail newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieDetail oldItem, @NonNull MovieDetail newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public MovieDetail getSwipedData(int swipedPosition){
        return getItem(swipedPosition);
    }

    @NonNull
    @Override
    public MoieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movies,parent,false);
        return new MoieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoieFavoriteViewHolder holder, int position) {
        MovieDetail movieDetail = getItem(position);
        if (movieDetail != null){
            holder.bind(movieDetail);
        }
    }

    public static class MoieFavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvVote;
        final TextView tvReleaseDate;
        final TextView tvDescription;
        final ImageView imgPoster;

        public MoieFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVote = itemView.findViewById(R.id.vote_average);
            tvReleaseDate = itemView.findViewById(R.id.tv_director);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
        void bind(final MovieDetail movie){
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
                    .into(imgPoster);
        }

    }
}
