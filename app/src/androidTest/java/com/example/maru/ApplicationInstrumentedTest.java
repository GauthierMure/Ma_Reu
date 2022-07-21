package com.example.maru;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
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

@RunWith(AndroidJUnit4.class)
public class ApplicationInstrumentedTest {

    private int ITEMS_COUNT;

    private ApiServiceMeeting apiServiceMeeting;
    private ApiServiceMeetingRoom apiServiceMeetingRoom;

    @Before
    public void setup(){
        apiServiceMeeting = DI.getMeetingApiService();
        apiServiceMeetingRoom = DI.getNewInstanceMeetingRoomApiService();
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

    //@Test
    /*public void MeetingList_FilterByRoomA_ShouldDisplayOneChild(){
        ActivityScenario<HomePageActivity> scenario = mActivityRule.getScenario();
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        try {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        }catch (Exception e){}
        //onView(withId(R.id.ibtnFilter)).perform(click());
        onView(withText("lieu")).perform(click());



        //onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(1)));
    }*/

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

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));

        onView(withId(R.id.IBtnaddMeeting)).perform(click());
        onView(withId(R.id.createMeetingFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.createMeetingBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.createMeetingBtn)).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT+=1)));
    }

}