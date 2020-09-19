package com.example.schoolscheduler.DayManagement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.schoolscheduler.R;
import com.example.schoolscheduler.SequentialSchedule.MyItemKeyProvider;
import com.example.schoolscheduler.Start.StartViewModel;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class DayManagementFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ArrayList<Lesson> mValues = new ArrayList<>();

    private DayManagementViewModel viewModel;

    private DayManagementFragmentRecyclerViewAdapter adapter;

    private SelectionTracker<Long> tracker;

    //TEMPORARY SOLUTION
    private int newLessonIndex;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayManagementFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DayManagementFragment newInstance(int columnCount) {
        DayManagementFragment fragment = new DayManagementFragment();
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
        View view = inflater.inflate(R.layout.day_fragment_item_list, container, false);

        viewModel  = new ViewModelProvider(this).get(DayManagementViewModel.class);

        viewModel.getLessonsFromDatabase(mValues);

        newLessonIndex = mValues.size() + 1;

        adapter = new DayManagementFragmentRecyclerViewAdapter(mValues);

        viewModel.getAddLesson().setValue(false);

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lesson_list);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(adapter);
            tracker = getMySpecificTracker(recyclerView);
            adapter.setTracker(tracker);


        // observe addLesson LiveData
        final Observer<Boolean> addLessonObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFalseAddLesson();
                    addLesson();
                }
            }
        };

        viewModel.getAddLesson().observe(getViewLifecycleOwner(), addLessonObserver);

        Button addLessonButton = (Button)view.findViewById(R.id.add_lesson_button);
        // update addLesson LiveData
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ADD LESSON", "KLIKNIĘTO");
                viewModel.setTrueAddLesson();
            }
        });

        return view;
    }

    private SelectionTracker<Long> getMySpecificTracker(RecyclerView recyclerView){
        return new SelectionTracker.Builder<>(
                "day_management_selection",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new  MyItemDetailsLookup(recyclerView) ,
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.<Long>createSelectAnything()
                )
                .build();
    }

    private void addLesson(){
        Log.i("ADD LESSON", "DODAJĘ GEOGRAFIĘ");
        mValues.add(new Lesson(newLessonIndex,"Geografia","Poniedziałek","13:40 - 14:25"));
        adapter.notifyItemInserted(newLessonIndex);
        newLessonIndex++;
    }
}