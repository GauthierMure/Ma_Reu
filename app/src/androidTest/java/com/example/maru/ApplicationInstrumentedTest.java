package com.example.maru;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.View.Activity.HomePageActivity;
import com.example.maru.View.Fragment.customDialogFragment;
import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;
import com.example.maru.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class ApplicationInstrumentedTest {

    private int ITEMS_COUNT;

    private ApiServiceMeeting apiServiceMeeting;

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    @Before
    public void setup(){
        apiServiceMeeting = DI.getMeetingApiService();
        ITEMS_COUNT = apiServiceMeeting.getMeetings().size();
    }

    @Test
    public void MeetingList_ShouldNotBeEmpty(){
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
    }

    @Test
    public void MeetingList_deleteAction_shouldRemoveItem() {
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        onView(withId(R.id.rvListMeeting)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT-=1)));
    }

    @Test
    public void MeetingList_FilterByRoomA_ShouldDisplayOneChild(){
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        scenario.recreate();

        onView(withId(R.id.ibtnFilter)).perform(click());
        onView(withText("annuler")).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        onView(withId(R.id.ibtnFilter)).perform(click());
        onView(withText("lieu")).perform(click());

        onView(withId(R.id.filterListRv)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.filterSelectBtn)).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(1)));
    }

    @Test
    public void MeetingList_FilterByDate_ShouldDisplayOneChild(){
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        scenario.recreate();

        onView(withId(R.id.ibtnFilter)).perform(click());
        onView(withText("annuler")).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        onView(withId(R.id.ibtnFilter)).perform(click());
        onView(withText("date")).perform(click());

        onView(withId(R.id.datefilterPicker)).perform(PickerActions.setDate(2022,5,30));
        onView(withId(R.id.selectDateFilterBtn)).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(1)));
    }

    @Test
    public void MeetingList_ItemClic_ShouldDisplayMeetingInfo(){
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        scenario.recreate();
        onView(withId(R.id.rvListMeeting)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Meeting meeting = apiServiceMeeting.getMeetings().get(1);
        onView(withId(R.id.meetingInfoNameTv)).check(matches(withText(meeting.getTitle())));
        onView(withId(R.id.iBtnAddMeetingReturn)).perform(click());
    }

    @Test
    public void MeetingList_addMeeting(){
        ActivityScenario<HomePageActivity> scenario = ActivityScenario.launch(HomePageActivity.class);
        scenario.recreate();

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        onView(withId(R.id.IBtnaddMeeting)).perform(click());
        onView(withId(R.id.createMeetingBtn)).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT+=1)));
    }

}