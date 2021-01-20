package com.example.creativefilm.ui.favorite.tvshow;

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
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.ui.detail.DetailActivity;

public class TvShowFavoriteAdapter extends PagedListAdapter<TvShowDetail, TvShowFavoriteAdapter.TvShowFavoriteViewHolder> {
    protected TvShowFavoriteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TvShowDetail> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvShowDetail>() {
                @Override
                public boolean areItemsTheSame(@NonNull TvShowDetail oldItem, @NonNull TvShowDetail newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TvShowDetail oldItem, @NonNull TvShowDetail newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public TvShowDetail getSwipedData(int swipedPosition){
        return getItem(swipedPosition);
    }

    @NonNull
    @Override
    public TvShowFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_tvshows,parent,false);
        return new TvShowFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavoriteViewHolder holder, int position) {
        TvShowDetail tvShowDetail = getItem(position);
        holder.bind(tvShowDetail);
    }

    public static class TvShowFavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvVoteAverage;
        final TextView tvDirector;
        final TextView tvDescrption;
        final ImageView img_poster;
        public TvShowFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVoteAverage = itemView.findViewById(R.id.vote_average);
            tvDirector = itemView.findViewById(R.id.tv_director);
            tvDescrption = itemView.findViewById(R.id.tv_description);
            img_poster = itemView.findViewById(R.id.img_poster);
        }

        void bind(final TvShowDetail tvShow){
            tvTitle.setText(tvShow.getTitle());
            tvVoteAverage.setText(String.valueOf(tvShow.getVoteAverage()));
            tvDirector.setText(tvShow.getReleaseDate());
            tvDescrption.setText(tvShow.getOverview());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TVSHOW, tvShow.getId());
                itemView.getContext().startActivity(intent);
            });

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+ tvShow.getPosterPath())
                    .into(img_poster);
        }
    }
}
