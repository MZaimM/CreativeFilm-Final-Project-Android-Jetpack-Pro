package com.example.creativefilm.ui.home;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.utils.DataDummy;
import com.example.creativefilm.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class HomeActivityTest {
    private final List<Movie> dummyMovie = DataDummy.generateDummyMovie();
    private final ArrayList<MovieDetail> dummyMovieDetail = DataDummy.generateDummyMovieDetail();

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }
    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadMovie() {
        onView(withText("MOVIES")).perform(click());
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()));
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.scrollToPosition(dummyMovie.size()));
    }

    @Test
    public void loadDetailMovie() {
        onView(withText("MOVIES")).perform(click());
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.image_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.image_back_drop)).check(matches(isDisplayed()));

        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title_detail)).check(matches(withText(dummyMovieDetail.get(0).getTitle())));

        onView(withId(R.id.tv_release_date_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_release_date_detail)).check(matches(withText(dummyMovieDetail.get(0).getReleaseDate())));

        onView(withId(R.id.tv_description_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_description_detail)).check(matches(withText(dummyMovieDetail.get(0).getOverview())));

    }

    @Test
    public void loadOtherMovie() {
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.rvOtherMovie)).perform(ViewActions.scrollTo());
        onView(withId(R.id.rvOtherMovie)).check(matches(isDisplayed()));
        onView(withId(R.id.rvOtherMovie)).perform(RecyclerViewActions.scrollToPosition(dummyMovie.size()));
    }

    @Test
    public void loadShareMovie() {
        onView(withText("MOVIES")).perform(click());
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.btn_share)).perform(ViewActions.scrollTo());
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()));
    }

}