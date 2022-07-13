package com.example.maru;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static com.example.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.View.Activity.HomePageActivity;
import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;
import com.example.maru.utils.DeleteViewAction;

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
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);

    @Before
    public void setup(){
        apiServiceMeeting = DI.getMeetingApiService();
        apiServiceMeetingRoom = DI.getMeetingRoomApiService();
        meetingList = apiServiceMeeting.getMeetings();
        meetingRoomList = apiServiceMeetingRoom.getMeetingRooms();
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity,notNullValue());
    }

    @Test
    public void MeetingList_ShouldNotBeEmpty(){
        onView(withId(R.id.rvListMeeting)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void MeetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.rvListMeeting)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.rvListMeeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.rvListMeeting)).check(withItemCount(ITEMS_COUNT-1));
    }
}