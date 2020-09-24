package com.example.schoolscheduler.SequentialSchedule;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolscheduler.SequentialSchedule.SequentialScheduleRecyclerViewAdapter;
import com.example.schoolscheduler.R;
import com.example.schoolscheduler.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SequentialScheduleFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private SequentialScheduleRecyclerViewAdapter adapter;

    private SelectionTracker<String> tracker;
    private final List<String> weekdays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SequentialScheduleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SequentialScheduleFragment newInstance(int columnCount) {
        SequentialScheduleFragment fragment = new SequentialScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sequential_schedule_list, container, false);

        adapter = new SequentialScheduleRecyclerViewAdapter(weekdays);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
            tracker = getMySpecificTracker(recyclerView);
            adapter.setTracker(tracker);
        }

        tracker.addObserver(new SelectionTracker.SelectionObserver<String>(){
            @Override
            public void onSelectionChanged(){
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

    private void navigateToDayManagementFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_sequentialScheduleFragment_to_dayManagementFragmentFragment);
    }
}