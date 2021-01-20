package com.example.creativefilm.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.ui.detail.DetailActivity;

import java.util.List;

public class OtherMovieAdapter extends RecyclerView.Adapter<OtherMovieAdapter.OtherMovieViewHolder> {
    private List<Movie> listOtherMovie;


    public OtherMovieAdapter(List<Movie> listOtherMovie) {
        this.listOtherMovie = listOtherMovie;
        notifyDataSetChanged();
    }


    public void setOtherMovie(List<Movie> otherMovie){
        this.listOtherMovie = otherMovie;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OtherMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_movie, parent, false);
        return new OtherMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherMovieViewHolder holder, int position) {
        Movie movie = listOtherMovie.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return listOtherMovie.size();
    }

    static class OtherMovieViewHolder extends RecyclerView.ViewHolder{

        final ImageView img_poster;
        final TextView tvVoteAverage;
        public OtherMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_other_poster);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_avarage);
        }

        void bind(final Movie movie){
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+movie.getPosterPath())
                    .into(img_poster);
            tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));


            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.getId());
                itemView.getContext().startActivity(intent);
            });
        }

    }
}
