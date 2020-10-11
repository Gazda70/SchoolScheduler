package com.example.schoolscheduler.LessonManagement;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.LessonEquipmentCrossRef;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.Collections;
import java.util.List;

public class LessonManagementViewModel extends ViewModel {

    private Lesson currentLesson;

    public Lesson getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(Lesson currentLesson) {
        this.currentLesson = currentLesson;
    }

    private List<Equipment> myEquipment;

    public List<Equipment> getMyEquipment(){
        if(this.myEquipment == null) {
            this.myEquipment = Collections.emptyList();
        }
        return this.myEquipment;
    };

    private MutableLiveData<Boolean> addEquipment;
    public MutableLiveData<Boolean> getAddEquipment() {
        if (addEquipment == null) {
            addEquipment = new MutableLiveData<Boolean>();
        }
        return addEquipment;
    }
    public void setTrueAddEquipment(){
        getAddEquipment().setValue(true);
    }
    public void setFalseAddEquipment(){
        getAddEquipment().setValue(false);
    }

    private MutableLiveData<Boolean> lessonNameEntered;
    public MutableLiveData<Boolean> getLessonNameEntered() {
        if (lessonNameEntered == null) {
            lessonNameEntered = new MutableLiveData<Boolean>();
        }
        return lessonNameEntered;
    }
    public void setTrueLessonNameEntered(){ getLessonNameEntered().setValue(true); }
    public void setFalseLessonNameEntered(){ getLessonNameEntered().setValue(false); }

    private MutableLiveData<Boolean> lessonDurationEntered;
    public MutableLiveData<Boolean> getLessonDurationEntered() {
        if (lessonDurationEntered == null) {
            lessonDurationEntered = new MutableLiveData<Boolean>();
        }
        return lessonDurationEntered;
    }
    public void setTrueLessonDurationEntered(){ getLessonDurationEntered().setValue(true); }
    public void setFalseLessonDurationEntered(){ getLessonDurationEntered().setValue(false); }

    private MutableLiveData<Boolean> lessonEditionDone;
    public MutableLiveData<Boolean> getLessonEditionDone() {
        if (lessonEditionDone == null) {
            lessonEditionDone = new MutableLiveData<Boolean>();
        }
        return lessonEditionDone;
    }
    public void setTrueLessonEditionDone(){ getLessonEditionDone().setValue(true); }
    public void setFalseLessonEditionDone(){
        getLessonEditionDone().setValue(false);
    }

 /*   public void getEquipmentFromDatabase()
    {
        final Observer<List<LessonEquipmentCrossRef>> crossRefObserver = new Observer<List<LessonEquipmentCrossRef>>() {
            @Override
            public void onChanged(List<LessonEquipmentCrossRef> lessonEquipmentCrossRef) {
                for(int i = 0; i < lessonEquipmentCrossRef.size(); i++){
                    getEquipment(lessonEquipmentCrossRef.get(i).eqId);
                }
                myEquipment = temporaryEquipment;
            }

         };
      //  ScheduleDatabase.getInstance().scheduleDao().getLessonEqCrossRefForLessonId(currentLesson.lessonId).observe(myLifecycleOwner, crossRefObserver);
    }


    private void getEquipment(int eqId){

        final Observer<Equipment> eqObserver = new Observer<Equipment>() {
            @Override
            public void onChanged(Equipment lessonEquipment) {
                temporaryEquipment.add(lessonEquipment);
            }

        };
   //     ScheduleDatabase.getInstance().scheduleDao().getEqById(eqId).observe(myLifecycleOwner, eqObserver);
    }*/

    @SuppressLint("StaticFieldLeak")
    public void insertLesson(){
        new InsertAsyncTask(currentLesson).execute(currentLesson);
    }

    private class InsertAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private Lesson toInsert;

        InsertAsyncTask(Lesson toInsert){
            this.toInsert = toInsert;
        };

        @Override
        protected Void doInBackground(Lesson... lessons) {
            ScheduleDatabase.getInstance().scheduleDao().insertLesson(toInsert);
            Log.i("INSERT ASYNC TASK", "INSERTING LESSON");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("ASYNC TASK", "FINISHED");
        }
    }

    public void updateLesson(){
        new UpdateAsyncTask(currentLesson).execute();
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        private Lesson toUpdate;

        UpdateAsyncTask(Lesson toUpdate){
            this.toUpdate = toUpdate;
        };

        @Override
        protected Void doInBackground(Void... voids) {
            ScheduleDatabase.getInstance().scheduleDao().updateLesson(toUpdate);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("ASYNC TASK", "FINISHED");
        }
    }
}
