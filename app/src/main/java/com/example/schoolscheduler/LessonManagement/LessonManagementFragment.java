package com.example.schoolscheduler.LessonManagement;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.schoolscheduler.R;
import com.example.schoolscheduler.SharedViewModels.DayManagementLessonManagementVM;
import com.example.schoolscheduler.SharedViewModels.SequentialScheduleDayManagementVM;
import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class LessonManagementFragment extends Fragment {

    private LessonManagementViewModel viewModel;

    private DayManagementLessonManagementVM sharedVMWithDM;

    private LessonManagementRecyclerViewAdapter adapter;

    private View view;

    private SelectionTracker<Long> tracker;

    private String actualEquipmentDesc;
    //TEMPORARY SOLUTION
    private int newEqIndex;


    public LessonManagementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedVMWithDM = new ViewModelProvider(requireActivity()).get(DayManagementLessonManagementVM.class);

        view = inflater.inflate(R.layout.fragment_lesson_management_list, container, false);

        viewModel = new ViewModelProvider(this).get(LessonManagementViewModel.class);

        actualEquipmentDesc = "";

        viewModel.setFalseLessonEditionDone();
        viewModel.setFalseAddEquipment();
        viewModel.setFalseLessonNameEntered();
        viewModel.setFalseLessonDurationEntered();

        // observe chosenLesson LiveData
        final Observer<Lesson> addLessonObserver = new Observer<Lesson>() {
            @Override
            public void onChanged(Lesson toDisplay) {
                viewModel.setCurrentLesson(toDisplay);
                setLessonContentsFields();
            }
        };

        sharedVMWithDM.getChosenLesson().observe(getViewLifecycleOwner(), addLessonObserver);

        adapter = new LessonManagementRecyclerViewAdapter(viewModel.getMyEquipment());

        setUpRecyclerView();

     /*   ZMIANA NA ZWYKŁĄ ZMIENNA boolean
     // observe workingOnExistingLesson LiveData
        final Observer<Boolean> workingOnExistingLessonObserver = new Observer<Boolean>(){

            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                 //   viewModel.getEquipmentFromDatabase();
                }
            }
        };

        sharedVM.getWorkingOnExistingLesson().observe(getViewLifecycleOwner(), workingOnExistingLessonObserver);*/

        // observe lessonNameEntered LiveData
        final Observer<Boolean> lessonNameEnteredObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    viewModel.getCurrentLesson().lessonName = getLessonNameFromUI();
                    viewModel.setFalseLessonNameEntered();
                }
            }
        };

        viewModel.getLessonNameEntered().observe(getViewLifecycleOwner(), lessonNameEnteredObserver);

        // observe lessonDurationEntered LiveData
        final Observer<Boolean> lessonDurationEnteredObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    viewModel.getCurrentLesson().lessonDuration = getLessonDurationFromUI();
                    viewModel.setFalseLessonDurationEntered();
                }
            }
        };

        viewModel.getLessonDurationEntered().observe(getViewLifecycleOwner(), lessonDurationEnteredObserver);


        // observe addEquipment LiveData
        final Observer<Boolean> addEquipmentObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFalseAddEquipment();
                   // addEquipment();
                }
            }
        };

        viewModel.getAddEquipment().observe(getViewLifecycleOwner(), addEquipmentObserver);

        //observe lessonEditionDone Live Data
        final Observer<Boolean> lessonEditionDone = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setFalseLessonEditionDone();
                    onLessonEditionDone();
                }
            }
        };

        viewModel.getLessonEditionDone().observe(getViewLifecycleOwner(), lessonEditionDone);

        Button addEquipmentButton = (Button)view.findViewById(R.id.add_equipment_button);
        // update addEquipment LiveData
        addEquipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setTrueAddEquipment();
            }
        });

        Button doneManagingLessonButton = (Button)view.findViewById(R.id.done_managing_lesson_button);
        // update lessonEditionDone LiveData
        doneManagingLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setTrueLessonEditionDone();
            }
        });

        EditText lessonNameField = (EditText)view.findViewById(R.id.lesson_name_inner);
        lessonNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setTrueLessonNameEntered();
            }
        });
        return view;
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

    private void navigateToDayManagementFragment(){
        if( NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.lessonManagementFragment2)
        {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_lessonManagementFragment2_to_dayManagementFragmentFragment);
        }
    }

    private boolean checkLessonNameField(){
        TextInputEditText lessonName = (TextInputEditText)view.findViewById(R.id.lesson_name_inner);
        boolean toReturn = false;
        if(lessonName.getText().toString().matches("")){
            Toast.makeText(getContext(), "You did not enter a lesson name", Toast.LENGTH_SHORT).show();
        }else{
            toReturn = true;
            viewModel.getCurrentLesson().lessonName = lessonName.getText().toString();
        }
        return toReturn;
    }

    private boolean checkLessonDurationField(){
        TextInputEditText lessonDuration = (TextInputEditText)view.findViewById(R.id.lesson_duration_inner);
        boolean toReturn = false;
        if(lessonDuration.getText().toString().matches("")){
            Toast.makeText(getContext(), "You did not enter lesson duration", Toast.LENGTH_SHORT).show();
        }else{
            toReturn = true;
            viewModel.getCurrentLesson().lessonDuration = lessonDuration.getText().toString();
        }
        return toReturn;
    }

    private boolean checkEquipmentDescriptionField(){
        TextInputEditText equipmentDescription = (TextInputEditText)view.findViewById(R.id.equipment_description_inner);
        boolean toReturn = false;
        actualEquipmentDesc = equipmentDescription.getText().toString();
        if(actualEquipmentDesc.matches("")){
            Toast.makeText(getContext(), "You did not enter equipment descritpion", Toast.LENGTH_SHORT).show();
        }else{
            toReturn = true;
        }
        return toReturn;
    }

    private void onLessonEditionDone() {
        if (checkLessonNameField() && checkLessonDurationField()) {
            Log.i("GOTTEN LESSON name", viewModel.getCurrentLesson().lessonName);
            Log.i("GOTTEN LESSON DURATION", viewModel.getCurrentLesson().lessonDuration);
            if (sharedVMWithDM.getWorkingOnExistingLesson()) {
                Log.i("ASYNC UPDATE", "UPDATING");
                viewModel.updateLesson();
                sharedVMWithDM.setWorkingOnExistingLesson(false);
            } else {
                viewModel.insertLesson();
            }
            navigateToDayManagementFragment();
        }
    }

    private void setLessonContentsFields(){
        TextInputEditText lessonName = (TextInputEditText)view.findViewById(R.id.lesson_name_inner);
        TextInputEditText lessonDuration = (TextInputEditText)view.findViewById(R.id.lesson_duration_inner);
        lessonName.setText(viewModel.getCurrentLesson().lessonName);
        lessonDuration.setText(viewModel.getCurrentLesson().lessonDuration);
    }

    private String getLessonNameFromUI(){
        TextInputEditText lessonName = (TextInputEditText)view.findViewById(R.id.lesson_name_inner);
        return lessonName.getText().toString();
    }

    private String getLessonDurationFromUI(){
        TextInputEditText lessonDuration = (TextInputEditText)view.findViewById(R.id.lesson_duration_inner);
        return lessonDuration.getText().toString();
    }

}