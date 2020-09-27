package com.example.schoolscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.schoolscheduler.DayManagement.DayManagementFragment;
import com.example.schoolscheduler.Start.StartFragment;
import com.example.schoolscheduler.database.ScheduleDatabase;


public class MainActivity extends AppCompatActivity{

    public ScheduleDatabase scheduleDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.my_NavHostFragment, StartFragment.newInstance())
                    .commitNow();

        }
        ApplicationContextHolder.setHeldApplicationContext(this);
        scheduleDatabase = Room.databaseBuilder(getApplicationContext(),
                ScheduleDatabase.class, "scheduleDatabase").build();

    }

    public ScheduleDatabase getScheduleDatabase() {
        return scheduleDatabase;
    }
}