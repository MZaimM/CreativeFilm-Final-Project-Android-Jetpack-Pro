package com.example.creativefilm.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.creativefilm.R;
import com.example.creativefilm.ui.movies.MoviesFragment;
import com.example.creativefilm.ui.tvshow.TvShowsFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    public static final int[] TAB_TITLES = new int[]{
            R.string.movies, R.string.tvShows};
    private final Context context;

    public SectionPagerAdapter(Context context, FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MoviesFragment();
            case 1:
                return new TvShowsFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
}
