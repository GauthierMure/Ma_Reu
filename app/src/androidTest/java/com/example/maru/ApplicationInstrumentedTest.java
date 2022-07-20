package com.example.maru;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
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

    private final int ITEMS_COUNT = 3;

    private ApiServiceMeeting apiServiceMeeting;
    private ApiServiceMeetingRoom apiServiceMeetingRoom;
    private List<Meeting> meetingList;
    private List<MeetingRoom> meetingRoomList;
    private HomePageActivity mActivity;

    @Rule
    public ActivityScenarioRule<HomePageActivity> mActivityRule = new ActivityScenarioRule<>(HomePageActivity.class);

    @Before
    public void setup(){
        apiServiceMeeting = DI.getMeetingApiService();
        apiServiceMeetingRoom = DI.getMeetingRoomApiService();
        meetingList = apiServiceMeeting.getMeetings();
        meetingRoomList = apiServiceMeetingRoom.getMeetingRooms();
    }

    @Test
    public void MeetingList_ShouldNotBeEmpty(){
        ActivityScenario<HomePageActivity> scenario = mActivityRule.getScenario();
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
    }

    @Test
    public void MeetingList_deleteAction_shouldRemoveItem() {
        ActivityScenario<HomePageActivity> scenario = mActivityRule.getScenario();
        // Given : We remove the element at position 2
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));
        // When perform a click on a delete icon
        onView(withId(R.id.rvListMeeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT-1)));
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
        ActivityScenario<HomePageActivity> scenario = mActivityRule.getScenario();
        onView(withId(R.id.rvListMeeting)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Meeting meeting = meetingList.get(1);
        onView(withId(R.id.meetingInfoNameTv)).check(matches(withText(meeting.getTitle())));
    }

    @Test
    public void MeetingList_addMeeting(){
        ActivityScenario<HomePageActivity> scenario = mActivityRule.getScenario();
        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT)));

        onView(withId(R.id.IBtnaddMeeting)).perform(click());
        onView(withId(R.id.createMeetingBtn)).perform(click());

        onView(withId(R.id.rvListMeeting)).check(matches(hasChildCount(ITEMS_COUNT+1)));
    }

}