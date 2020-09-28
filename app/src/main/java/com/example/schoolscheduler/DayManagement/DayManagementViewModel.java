package com.example.schoolscheduler.DayManagement;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;

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

  /*  private void addLesson(String lessonName, String lessonDay, String lessonDuration){
        addLessonToDatabase(newLessonIndex,lessonName, lessonDay,lessonDuration);
        adapter.notifyItemInserted(newLessonIndex);
    }

    public void addLessonToDatabase(int lessonId, String lessonName, String lessonDay, String lessonDuration){
        ScheduleDatabase.getInstance().scheduleDao().insert(lessonId, lessonName, lessonDay, lessonDuration);
    }*/
}
