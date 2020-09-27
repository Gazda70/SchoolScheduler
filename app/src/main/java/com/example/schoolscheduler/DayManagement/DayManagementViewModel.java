package com.example.schoolscheduler.DayManagement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayManagementViewModel extends ViewModel {

    private String dayName;
    private MutableLiveData<Boolean> addLesson;
    public MutableLiveData<Boolean> getAddLesson() {
        if (addLesson == null) {
            addLesson = new MutableLiveData<Boolean>();
        }
        return addLesson;
    }
    public void setTrueAddLesson(){
        addLesson.setValue(true);
    }
    public void setFalseAddLesson(){
        addLesson.setValue(false);
    }

    public void getLessonsFromDatabase(ArrayList<Lesson> toPopulate){

       // toPopulate.addAll(ScheduleDatabase.getInstance().scheduleDao().getLessonsForDay(dayName).);
    }
}
