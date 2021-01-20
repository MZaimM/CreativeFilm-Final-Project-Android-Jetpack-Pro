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
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.ui.detail.DetailActivity;

import java.util.List;

public class OtherTvShowAdapter extends RecyclerView.Adapter<OtherTvShowAdapter.OtherTvSHowViewHolder> {

    private List<TvShow> listOtherTvShows;

    public OtherTvShowAdapter(List<TvShow> listOtherTvShows) {
        this.listOtherTvShows = listOtherTvShows;
        notifyDataSetChanged();
    }


    public void setOtherTvShow(List<TvShow> otherTvShow){
        this.listOtherTvShows = otherTvShow;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherTvSHowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_movie, parent,false);
        return new OtherTvSHowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherTvSHowViewHolder holder, int position) {
        TvShow tvShow = listOtherTvShows.get(position);
        holder.bind(tvShow);
    }

    @Override
    public int getItemCount() {
        return listOtherTvShows.size();
    }

    static class OtherTvSHowViewHolder extends RecyclerView.ViewHolder{
        final ImageView img_poster;
        final TextView tvVoteAverage;

        public OtherTvSHowViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_other_poster);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_avarage);
        }

        void bind(final TvShow tvShow){
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2"+tvShow.getPosterPath())
                    .into(img_poster);
            tvVoteAverage.setText(String.valueOf(tvShow.getVoteAverage()));


            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TVSHOW, tvShow.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
