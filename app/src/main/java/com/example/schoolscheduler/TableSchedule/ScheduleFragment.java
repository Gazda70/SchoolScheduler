package com.example.schoolscheduler.TableSchedule;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.schoolscheduler.R;

import com.example.schoolscheduler.databinding.FragmentScheduleBinding;

import java.util.Objects;


public class ScheduleFragment extends Fragment {

    private ScheduleViewModel mViewModel;

    private FragmentScheduleBinding binding;

    private Context myContext ;


    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule,container, false);
        mViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        myContext= this.getContext();

        final Observer<Boolean> rowAddedObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Log.i("ADD ROW","DODAWANIE");
                    mViewModel.addRowToSchedule(binding.tableBody,myContext);
                    mViewModel.setOnRowAdded(false);
                }
            }
        };

        mViewModel.getOnRowAdded().observe(this.requireActivity(), rowAddedObserver);

        binding.addRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ADD ROW","NACISNIETO");
                mViewModel.setOnRowAdded(true);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        // TODO: Use the ViewModel
    }

}