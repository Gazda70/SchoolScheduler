package com.example.schoolscheduler.SequentialSchedule;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolscheduler.SequentialSchedule.SequentialScheduleRecyclerViewAdapter;
import com.example.schoolscheduler.R;
import com.example.schoolscheduler.SharedViewModels.SequentialScheduleDayManagementVM;
import com.example.schoolscheduler.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SequentialScheduleFragment extends Fragment {

    private SequentialScheduleDayManagementVM sharedVM;

    private SequentialScheduleRecyclerViewAdapter adapter;

    private SelectionTracker<String> tracker;

    private View view;

    private final List<String> weekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

    public SequentialScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sequential_schedule_list, container, false);

        sharedVM = new ViewModelProvider(requireActivity()).get(SequentialScheduleDayManagementVM.class);

        adapter = new SequentialScheduleRecyclerViewAdapter(weekdays);

        setupRecyclerView();

        tracker.addObserver(new SelectionTracker.SelectionObserver<String>(){
            @Override
            public void onSelectionChanged(){
                sharedVM.setChosenDay(tracker.getSelection().iterator().next());
                navigateToDayManagementFragment();

            }
        });

        return view;
    }

    private SelectionTracker<String> getMySpecificTracker(RecyclerView recyclerView){
        return new SelectionTracker.Builder<>(
                "sequential_schedule_selection",
                recyclerView,
                new SSItemKeyProvider(adapter),
                new SSItemDetailsLookup(recyclerView),
                StorageStrategy.createStringStorage())
                .withSelectionPredicate(
                        SelectionPredicates.<String>createSelectAnything()
                )
                .build();
    }

    private void setupRecyclerView(){
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            tracker = getMySpecificTracker(recyclerView);
            adapter.setTracker(tracker);
        }
    }

    private void navigateToDayManagementFragment(){
        if(NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.sequentialScheduleFragment)
        {
            Log.i("NAVIGATOR", tracker.getSelection().iterator().next());
            NavHostFragment.findNavController(this).navigate(R.id.action_sequentialScheduleFragment_to_dayManagementFragmentFragment);
        }
    }
}