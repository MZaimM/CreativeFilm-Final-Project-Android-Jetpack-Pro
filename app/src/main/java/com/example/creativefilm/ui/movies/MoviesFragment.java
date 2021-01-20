package com.example.creativefilm.ui.movies;

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
import com.example.creativefilm.ui.adapter.MoviesAdapter;
import com.example.creativefilm.ui.favorite.movie.MovieFavoriteActivity;
import com.example.creativefilm.utils.viewmodel.ViewModelFactory;
import com.getbase.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private  MoviesViewModel viewModel;
    private MoviesAdapter moviesAdapter;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;


    public MoviesFragment() {
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
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rvMovies);
        progressBar = view.findViewById(R.id.progress_bar);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(MoviesViewModel.class);

        moviesAdapter = new MoviesAdapter();

        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(moviesAdapter);

        setTopRatedMovie();

        fab1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MovieFavoriteActivity.class);
            getActivity().startActivity(intent);
        });

        fab2.setOnClickListener(v -> {
            viewModel.getNewestMovie().observe(requireActivity(), movies -> moviesAdapter.submitList(movies));
            Toast.makeText(getActivity(), "Newest Movie", Toast.LENGTH_SHORT).show();
        });

        fab3.setOnClickListener(v -> {
            setTopRatedMovie();
            Toast.makeText(getActivity(), "Top Rated", Toast.LENGTH_SHORT).show();
        });

    }

    public void setTopRatedMovie(){
        viewModel.getMovie().observe(requireActivity(), movies -> {
            if (movies != null){
                switch (movies.status){
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        progressBar.setVisibility(View.GONE);
                        moviesAdapter.submitList(movies.data);
                        moviesAdapter.notifyDataSetChanged();
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