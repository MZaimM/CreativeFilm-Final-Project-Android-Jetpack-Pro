package com.example.creativefilm.ui.splash;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.creativefilm.R;
import com.example.creativefilm.data.source.local.entity.Movie;
import com.example.creativefilm.data.source.local.entity.MovieDetail;
import com.example.creativefilm.data.source.local.entity.TvShow;
import com.example.creativefilm.data.source.local.entity.TvShowDetail;
import com.example.creativefilm.ui.home.HomeActivity;
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
import static org.hamcrest.Matchers.allOf;

public class SplashActivityTest {
    private final List<TvShow> dummyTvShows =  DataDummy.generateDummyTvShows();
    private final ArrayList<TvShowDetail> dummyTvShowDetail = DataDummy.generateDummyTvShowDetail();


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
    public void loadTvShows() {
        onView(withText("TV SHOWS")).perform(click());
        onView(withId(R.id.rvTvShows)).check(matches(isDisplayed()));
        onView(withId(R.id.rvTvShows)).perform(RecyclerViewActions.scrollToPosition(dummyTvShows.size()));
    }



    @Test
    public void loadDetailTvShows() {
        onView(withText("TV SHOWS")).perform(click());
        onView(withId(R.id.rvTvShows)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        //onView(withId(R.id.tv_description_detail)).perform(ViewActions.scrollTo());

        onView(withId(R.id.image_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.image_back_drop)).check(matches(isDisplayed()));

        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title_detail)).check(matches(withText(dummyTvShowDetail.get(0).getTitle())));

        onView(withId(R.id.tv_release_date_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_release_date_detail)).check(matches(withText(dummyTvShowDetail.get(0).getReleaseDate())));

        onView(withId(R.id.tv_description_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_description_detail)).check(matches(withText(dummyTvShowDetail.get(0).getOverview())));

        onView(withId(R.id.fab_detail)).perform(click());
    }




    @Test
    public void loadOtherTvShows() {
        onView(withText("TV SHOWS")).perform(click());
        onView(withId(R.id.rvTvShows)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.rvOtherMovie)).perform(ViewActions.scrollTo());
        onView(withId(R.id.rvOtherMovie)).check(matches(isDisplayed()));
        onView(withId(R.id.rvOtherMovie)).perform(RecyclerViewActions.scrollToPosition(dummyTvShows.size()));
    }

    @Test
    public void loadShareTvShows() {
        onView(withText("TV SHOWS")).perform(click());
        onView(withId(R.id.rvTvShows)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.btn_share)).perform(ViewActions.scrollTo());
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()));

    }



}