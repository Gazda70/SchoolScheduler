package com.example.schoolscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.schoolscheduler.DayManagement.DayManagementFragment;
import com.example.schoolscheduler.Start.StartFragment;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.my_NavHostFragment, StartFragment.newInstance())
                    .commitNow();

        }
    }
}