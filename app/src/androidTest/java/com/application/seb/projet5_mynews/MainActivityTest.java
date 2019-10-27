package com.application.seb.projet5_mynews;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.application.seb.projet5_mynews.Activities.MainActivity;
import com.application.seb.projet5_mynews.Activities.NotificationsActivity;
import com.application.seb.projet5_mynews.Activities.SearchActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;



public class MainActivityTest {


    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);


    @Test
    public void toolbarTest() {
        onView(withId(R.id.toolbar_activity_main)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }

    @Test
    public void viewPagerTest() {
        onView(withId(R.id.activity_main_viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_tabs)).check(matches(isDisplayed()));
        onView(withText(R.string.top_stories_tabLayout_title)).check(matches(isDisplayed()));
        onView(withText(R.string.most_popular_tabLayout_title)).check(matches(isDisplayed()));
        onView(withText(R.string.business_tabLayout_title)).check(matches(isDisplayed()));

        onView(allOf(withText(R.string.top_stories_tabLayout_title),isDescendantOfA(withId(R.id.activity_main_tabs))))
                .perform(click())
                .check(matches(isSelected()));
        swipeLeft();

        onView(allOf(withText(R.string.most_popular_tabLayout_title),isDescendantOfA(withId(R.id.activity_main_tabs))))
                .perform(click())
                .check(matches(isSelected()));
        swipeLeft();

        onView(allOf(withText(R.string.business_tabLayout_title),isDescendantOfA(withId(R.id.activity_main_tabs))))
                .perform(click())
                .check(matches(isSelected()));
    }

    @Test
    public void menuMainTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_notifications)).perform(click());
        intended(hasComponent(NotificationsActivity.class.getName()));

        onView(isRoot()).perform(pressBack());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_help)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_about)).perform(click());
    }

    @Test
    public void searchButtonAndSearchActivityTest() {
        onView(withId(R.id.search_menu)).perform(click());
        intended(hasComponent(SearchActivity.class.getName()));

        onView(withId(R.id.search_click_button)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_search)).check(matches(isDisplayed()));
        onView(withId(R.id.begin_date_contener)).check(matches(isDisplayed()));
        onView(withId(R.id.end_date_contener)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_search_checkboxs_contener)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_notifications_line_and_switch_contener)).check(matches(not(isDisplayed())));

        onView(withId(R.id.search_click_button)).perform(click());
    }

}