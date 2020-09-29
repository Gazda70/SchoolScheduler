package com.example.schoolscheduler.Start;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.schoolscheduler.databinding.StartFragmentBinding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.schoolscheduler.R;



public class StartFragment extends Fragment {

    private StartViewModel mViewModel;
    private StartFragmentBinding binding;


    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // get the DataBindingClass
        binding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false);


        // get the ViewModel.
        mViewModel = new ViewModelProvider(this).get(StartViewModel.class);


        mViewModel.setFalseGoDays();
        mViewModel.setFalseGoEdit();

        // observe goEdit LiveData
        final Observer<Boolean> goEditObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mViewModel.setFalseGoEdit();
                    navigateToSchedule();
                }
            }
        };

        mViewModel.getGoEdit().observe(getViewLifecycleOwner(),goEditObserver);

        // update goEdit LiveData
            binding.generalPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setTrueGoEdit();
            }
        });


            // observe goDays LiveData
        final Observer<Boolean> goDaysObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newValue) {
                if (newValue) {
                    mViewModel.setFalseGoDays();
                    navigateToDays();
                }
            }
        };
        mViewModel.getGoDays().observe(getViewLifecycleOwner(),goDaysObserver);

        // update goDays LiveData
        binding.daysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setTrueGoDays();;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StartViewModel.class);
        // TODO: Use the ViewModel
    }

    private void navigateToSchedule(){
        if( NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.startFragment) {
            NavHostFragment.findNavController(this).navigate(R.id.action_startFragment_to_tableScheduleFragment2);
        }
    }

    private void navigateToDays(){
        if( NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.startFragment) {
            NavHostFragment.findNavController(this).navigate(R.id.action_startFragment_to_sequentialScheduleFragment);
        }
    }
}