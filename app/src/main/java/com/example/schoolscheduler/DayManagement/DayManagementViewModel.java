package com.example.schoolscheduler.DayManagement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayManagementViewModel extends ViewModel {

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

        if(toPopulate.isEmpty())
        {
            toPopulate.add(new Lesson(1,"Matematyka","Poniedziałek", "8:00 - 8:45"));
        }
       /* toPopulate.add(new Lesson(2,"J.polski","Poniedziałek", "8:55 - 9:40"));
        toPopulate.add(new Lesson(3,"J.polski","Poniedziałek", "9:50 - 10:35"));
        toPopulate.add(new Lesson(4,"Fizyka","Poniedziałek", "10:50 - 11:35"));
        toPopulate.add(new Lesson(5,"Chemia","Poniedziałek", "11:45 - 12:30"));
        toPopulate.add(new Lesson(6,"Informatyka","Poniedziałek", "12:40 - 13:25"));*/
    }
}
