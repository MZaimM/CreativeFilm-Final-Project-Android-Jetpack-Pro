package com.example.creativefilm.ui.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativefilm.R;
import com.example.creativefilm.ui.adapter.TvShowsAdapter;
import com.example.creativefilm.ui.favorite.tvshow.TvShowFavoriteActivity;
import com.example.creativefilm.utils.viewmodel.ViewModelFactory;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class TvShowsFragment extends Fragment {
    private RecyclerView rvTvShows;
    private ProgressBar progressBar;
    private TvShowsViewModel viewModel;
    private  TvShowsAdapter tvShowsAdapter;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    public TvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Rename and change types of parameters
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShows = view.findViewById(R.id.rvTvShows);
        progressBar = view.findViewById(R.id.progress_bar);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        viewModel = new ViewModelProvider(this,factory).get(TvShowsViewModel.class);

        tvShowsAdapter = new TvShowsAdapter();

        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);


        rvTvShows.setHasFixedSize(true);
        rvTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShows.setAdapter(tvShowsAdapter);

        setTopRatedTvShow();

        fab1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TvShowFavoriteActivity.class);
            getActivity().startActivity(intent);
            Toast.makeText(getActivity(), "Favorite", Toast.LENGTH_SHORT).show();
        });

        fab2.setOnClickListener(v -> {
            viewModel.getNewestTvShow().observe(requireActivity(), tvShows -> tvShowsAdapter.submitList(tvShows));
            Toast.makeText(getActivity(), "Newest Movie", Toast.LENGTH_SHORT).show();
        });

        fab3.setOnClickListener(v -> setTopRatedTvShow());
    }

    public void setTopRatedTvShow(){
        viewModel.getTvShows().observe(requireActivity(), tvShows -> {
            if (tvShows != null){
                switch (tvShows.status){
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        progressBar.setVisibility(View.GONE);
                        tvShowsAdapter.submitList(tvShows.data);
                        tvShowsAdapter.notifyDataSetChanged();
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}