package com.example.schoolscheduler.LessonManagement;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.schoolscheduler.R;
import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class LessonManagementFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ArrayList<Equipment> mValues = new ArrayList<>();

    private LessonManagementViewModel viewModel;

    private LessonManagementRecyclerViewAdapter adapter;

    private View view;

    private SelectionTracker<Long> tracker;


    //TEMPORARY SOLUTION
    private int newEqIndex;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LessonManagementFragment() {
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LessonManagementFragment newInstance(int columnCount) {
        LessonManagementFragment fragment = new LessonManagementFragment();
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
        view = inflater.inflate(R.layout.fragment_lesson_management_list, container, false);

        adapter = new LessonManagementRecyclerViewAdapter(mValues);

        viewModel = new ViewModelProvider(this).get(LessonManagementViewModel.class);

        viewModel.getEquipmentFromDatabase(mValues);

        newEqIndex = mValues.size() + 1;

        viewModel.getAddEquipment().setValue(false);

        setUpRecyclerView();

        // observe addEquipment LiveData
        final Observer<Boolean> addLessonObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFalseAddLesson();
                    addEquipment();
                }
            }
        };

        viewModel.getAddEquipment().observe(getViewLifecycleOwner(), addLessonObserver);

        Button addLessonButton = (Button)view.findViewById(R.id.add_equipment_button);
        // update addLesson LiveData
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setTrueAddLesson();
            }
        });

        return view;
        }

    private void addEquipment(){
        mValues.add(new Equipment(newEqIndex,"Piórnik"));
        adapter.notifyItemInserted(newEqIndex);
        newEqIndex++;
    }

    private void setUpRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lesson_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        tracker = getLMSpecificTracker(recyclerView);
        adapter.setTracker(tracker);
        LMSwipeToDeleteCallback swipeToDeleteCallback = new LMSwipeToDeleteCallback(getContext(),adapter, recyclerView);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private SelectionTracker<Long> getLMSpecificTracker(RecyclerView recyclerView){
        return new SelectionTracker.Builder<>(
                "lesson_management_selection",
                recyclerView,
                new LMLongKeyProvider(recyclerView),
                new LMItemDetailsLookup(recyclerView) ,
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.<Long>createSelectAnything()
                )
                .build();
    }
}