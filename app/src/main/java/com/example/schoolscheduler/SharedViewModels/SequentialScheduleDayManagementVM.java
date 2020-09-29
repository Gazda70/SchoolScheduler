package com.example.schoolscheduler.SharedViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SequentialScheduleDayManagementVM extends ViewModel {

    private MutableLiveData<String> chosenDay;
    public MutableLiveData<String> getChosenDay() {
        if (chosenDay == null) {
            chosenDay = new MutableLiveData<String>();
        }
        return chosenDay;
    }
    public void setChosenDay(String newDay){
        getChosenDay().setValue(newDay);
    }
}
