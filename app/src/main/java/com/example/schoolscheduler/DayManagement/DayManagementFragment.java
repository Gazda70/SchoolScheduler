package com.example.schoolscheduler.DayManagement;

import android.annotation.SuppressLint;
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

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.schoolscheduler.R;
import com.example.schoolscheduler.SharedViewModels.DayManagementLessonManagementVM;
import com.example.schoolscheduler.SharedViewModels.SequentialScheduleDayManagementVM;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.ArrayList;
import java.util.List;

public class DayManagementFragment extends Fragment {

    private SequentialScheduleDayManagementVM sharedVMWithSS;

    private DayManagementLessonManagementVM sharedVMWithLM;

    private DayManagementViewModel viewModel;

    private DayManagementFragmentRecyclerViewAdapter adapter;

    private View view;

    private SelectionTracker<Long> tracker;
    //TEMPORARY SOLUTION
    private int newLessonIndex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedVMWithSS = new ViewModelProvider(requireActivity()).get(SequentialScheduleDayManagementVM.class);

        sharedVMWithLM = new ViewModelProvider(requireActivity()).get(DayManagementLessonManagementVM .class);

        sharedVMWithLM.setWorkingOnExistingLesson(false);

        view = inflater.inflate(R.layout.day_fragment_item_list, container, false);

        viewModel  = new ViewModelProvider(this).get(DayManagementViewModel.class);

        //możliwy problem z indeksami w recyclerview - primary key z bazy danych może nie być odpowiednim indeksem dla listy
        adapter = new DayManagementFragmentRecyclerViewAdapter(new ArrayList<Lesson>());

        viewModel.setLifecycleOwner(getViewLifecycleOwner());

        viewModel.setFalseAddLesson();

        //możliwy problem z indeksami w recyclerview - primary key z bazy danych może nie być odpowiednim indeksem dla listy
        adapter = new DayManagementFragmentRecyclerViewAdapter(viewModel.getMyLessons());

        setUpRecyclerView();

        //observe chosenDay LiveData
        final Observer<String> chosenDayObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.setCurrentDay(s);
                viewModel.getLessonsFromDatabase(adapter);
            }
        };

        sharedVMWithSS.getChosenDay().observe(getViewLifecycleOwner(), chosenDayObserver);

        // observe addLesson LiveData
        final Observer<Boolean> addLessonObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFalseAddLesson();
                    setEmptyLessonAsChosenLesson();
                    Log.i("ADD LESSON","TRIGGERED");
                    //navigateToLessonManagementFragment();
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
               //sharedVMWithLM.setChosenLesson(tracker.getSelection().iterator().next());
                sharedVMWithLM.setWorkingOnExistingLesson(true);
                Log.i("UPDATE LESSON","TRIGGERED");
               // navigateToLessonManagementFragment();}
        }
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

    private void setEmptyLessonAsChosenLesson(){
        sharedVMWithLM.setChosenLesson(new Lesson("",viewModel.getCurrentDay(),""));
    }
    private void navigateToLessonManagementFragment(){
        if( NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.dayManagementFragmentFragment)
        {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dayManagementFragmentFragment_to_lessonManagementFragment2);
        }
    }
}