package com.example.schoolscheduler.DayManagement;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

public class DayManagementViewModel extends ViewModel {

    public void setMyLessons(List<Lesson> myLessons) {
        this.myLessons = myLessons;
    }

    private List<Lesson> myLessons;
    private MutableLiveData<Boolean> addLesson;
    private LifecycleOwner lifecycleOwner;

    private String currentDay;

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String newDay) {
        currentDay = newDay;
    }

    public List<Lesson> getMyLessons() {
        if (myLessons == null) {
            myLessons = Collections.emptyList();
        }
        return this.myLessons;
    }

    public void setLifecycleOwner(LifecycleOwner newLifecycleOwner) {
        this.lifecycleOwner = newLifecycleOwner;
    }


    public MutableLiveData<Boolean> getAddLesson() {
        if (addLesson == null) {
            addLesson = new MutableLiveData<Boolean>();
        }
        return addLesson;
    }

    public void setTrueAddLesson() {
        getAddLesson().setValue(true);
    }

    public void setFalseAddLesson() {
        getAddLesson().setValue(false);
    }

    public void getLessonsFromDatabase(DayManagementFragmentRecyclerViewAdapter adapter) {
        if(currentDay == null)
        {
            Log.i("VIEW MODEL", "GDZIE NAZWA DNIA???");
        }else{
            Log.i("VIEW MODEL", "MAM NAZWĘ DNIA");
            new GetLessonsAsyncTask(currentDay, adapter).execute();
        }
        for(int i = 0; i < myLessons.size(); i++){
            Log.i("DURATION OF LESSON NO:" + i, myLessons.get(i).lessonDuration);
        }
    }

    private  class GetLessonsAsyncTask extends AsyncTask<String , Void, List<Lesson>> {
        private String mDay;

        private DayManagementFragmentRecyclerViewAdapter mAdapter;

        GetLessonsAsyncTask(String theDay, DayManagementFragmentRecyclerViewAdapter theAdapter) {
            this.mDay = theDay;
            this.mAdapter = theAdapter;
        }
        @Override
        protected List<Lesson> doInBackground(String... strings) {

            List<Lesson> toPopulate = Collections.emptyList();
           toPopulate = ScheduleDatabase.getInstance().scheduleDao().getLessonsForDay(this.mDay);

            return toPopulate;
        }

        @Override
        protected void onPostExecute(List<Lesson> result) {
            mAdapter.setItems(result);
            mAdapter.notifyItemRangeChanged(0,result.size());
        }
    }
}
