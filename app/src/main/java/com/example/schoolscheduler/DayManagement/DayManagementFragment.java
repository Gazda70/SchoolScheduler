package com.example.schoolscheduler.DayManagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.schoolscheduler.R;
import com.example.schoolscheduler.database.Lesson;

import java.util.ArrayList;

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

    private View view;

    private SelectionTracker<Long> tracker;
    //TEMPORARY SOLUTION
    private int newLessonIndex;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayManagementFragment() {
    }

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
        view = inflater.inflate(R.layout.day_fragment_item_list, container, false);

        viewModel  = new ViewModelProvider(this).get(DayManagementViewModel.class);

        adapter = new DayManagementFragmentRecyclerViewAdapter(mValues);

        viewModel.getLessonsFromDatabase(mValues);

        newLessonIndex = mValues.size() + 1;

        viewModel.getAddLesson().setValue(false);

        setUpRecyclerView();

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
                viewModel.setTrueAddLesson();
            }
        });

        tracker.addObserver(new SelectionTracker.SelectionObserver<String>(){
            @Override
            public void onSelectionChanged(){
                navigateToLessonManagementFragment();}
        });
        return view;
    }

    private SelectionTracker<Long> getMySpecificTracker(RecyclerView recyclerView){
        return new SelectionTracker.Builder<>(
                "day_management_selection",
                recyclerView,
                new DMLongKeyProvider(recyclerView),
                new  DMItemDetailsLookup(recyclerView) ,
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.<Long>createSelectAnything()
                )
                .build();
    }

    private void addLesson(){
        mValues.add(new Lesson(newLessonIndex,"Geografia","Poniedziałek","13:40 - 14:25"));
        adapter.notifyItemInserted(newLessonIndex);
        newLessonIndex++;
    }

    private void setUpRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lesson_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        tracker = getMySpecificTracker(recyclerView);
        adapter.setTracker(tracker);
        DMSwipeToDeleteCallback swipeToDeleteCallback = new DMSwipeToDeleteCallback(getContext(),adapter, recyclerView);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void navigateToLessonManagementFragment(){
        if( NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.dayManagementFragmentFragment)
        {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dayManagementFragmentFragment_to_lessonManagementFragment2);
        }
    }
}