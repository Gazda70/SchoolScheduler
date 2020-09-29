package com.example.schoolscheduler.SharedViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;

public class DayManagementLessonManagementVM extends ViewModel {

    private MutableLiveData<Lesson> chosenLesson;
    public MutableLiveData<Lesson> getChosenLesson() {
        if (chosenLesson == null) {
            chosenLesson = new MutableLiveData<Lesson>();
        }
        return chosenLesson;
    }
    public void setChosenLesson(Lesson newLesson){
        getChosenLesson().setValue(newLesson);
    }
}
