package com.example.maru.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.maru.R;
import com.example.maru.ViewModel.MeetingListRecyclerViewAdapter;

public class HomePageActivity extends AppCompatActivity {

    private ImageButton mFilterBtn, mAddMeetingBtn;
    private RecyclerView mListMeetingRv;
    private PopupMenu mFilterMenu;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        init();
        initFilterMenu();
        setupBtns();
    }

    private void init(){
        mFilterBtn = findViewById(R.id.ibtnFilter);
        mAddMeetingBtn = findViewById(R.id.IBtnaddMeeting);
        mListMeetingRv = findViewById(R.id.rvListMeeting);
        mListMeetingRv.setLayoutManager(new LinearLayoutManager(this));
        mListMeetingRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mListMeetingRv.setAdapter(new MeetingListRecyclerViewAdapter());
    }

    private void initFilterMenu() {
        mFilterMenu = new PopupMenu(this,mFilterBtn);
        mMenu = mFilterMenu.getMenu();
        mFilterMenu.getMenuInflater().inflate(R.menu.filter_menu, mMenu);

        mFilterMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case 0:

                    return true;
                case 1:

                    return true;
            }

            return false;
        });

        mFilterBtn.setOnClickListener(v -> mFilterMenu.show());
    }

    private void setupBtns(){
        mAddMeetingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }

}