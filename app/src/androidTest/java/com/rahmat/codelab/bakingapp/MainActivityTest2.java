package com.rahmat.codelab.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.master_list_fragment_recipe),
                        childAtPosition(
                                allOf(withId(android.R.id.content),
                                        childAtPosition(
                                                withId(R.id.decor_content_parent),
                                                1)),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.master_list_fragment_recipe),
                        childAtPosition(
                                allOf(withId(android.R.id.content),
                                        childAtPosition(
                                                withId(R.id.decor_content_parent),
                                                1)),
                                0),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.master_list_fragment_recipe),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(R.id.decor_content_parent)))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.detail_text), isDisplayed()));
        appCompatTextView.perform(replaceText("- Graham Cracker crumbs (2.0 CUP)\n- unsalted butter, melted (6.0 TBLSP)\n- granulated sugar (0.5 CUP)\n- salt (1.5 TSP)\n- vanilla (5.0 TBLSP)\n- Nutella or other chocolate-hazelnut spread (1.0 K)\n- Mascapone Cheese(room temperature) (500.0 G)\n- heavy cream(cold) (1.0 CUP)\n- cream cheese(softened) (4.0 OZ)\n"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withText("List of Igridients"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("List of Igridients")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.detail_text), withText("- Graham Cracker crumbs (2.0 CUP)\n- unsalted butter, melted (6.0 TBLSP)\n- granulated sugar (0.5 CUP)\n- salt (1.5 TSP)\n- vanilla (5.0 TBLSP)\n- Nutella or other chocolate-hazelnut spread (1.0 K)\n- Mascapone Cheese(room temperature) (500.0 G)\n- heavy cream(cold) (1.0 CUP)\n- cream cheese(softened) (4.0 OZ)\n"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("- Graham Cracker crumbs (2.0 CUP) - unsalted butter, melted (6.0 TBLSP) - granulated sugar (0.5 CUP) - salt (1.5 TSP) - vanilla (5.0 TBLSP) - Nutella or other chocolate-hazelnut spread (1.0 K) - Mascapone Cheese(room temperature) (500.0 G) - heavy cream(cold) (1.0 CUP) - cream cheese(softened) (4.0 OZ) ")));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.step_detail_list),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        recyclerView4.check(matches(isDisplayed()));

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.step_detail_list),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        recyclerView5.check(matches(isDisplayed()));

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.step_detail_list), isDisplayed()));
        recyclerView6.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.videoplayer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.videoplayer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                0),
                        isDisplayed()));
        frameLayout2.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
