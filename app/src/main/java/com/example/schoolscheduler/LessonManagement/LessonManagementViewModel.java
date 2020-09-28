package com.example.schoolscheduler.LessonManagement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.ArrayList;

public class LessonManagementViewModel extends ViewModel {

    private MutableLiveData<Boolean> addEquipment;
    public MutableLiveData<Boolean> getAddEquipment() {
        if (addEquipment == null) {
            addEquipment = new MutableLiveData<Boolean>();
        }
        return addEquipment;
    }
    public void setTrueAddEquipment(){
        addEquipment.setValue(true);
    }
    public void setFalseAddEquipment(){
        addEquipment.setValue(false);
    }

    private MutableLiveData<Boolean> lessonNameEntered;
    public MutableLiveData<Boolean> getLessonNameEntered() {
        if (lessonNameEntered == null) {
            lessonNameEntered = new MutableLiveData<Boolean>();
        }
        return lessonNameEntered;
    }
    public void setTrueLessonNameEntered(){ lessonNameEntered.setValue(true); }
    public void setFalseLessonNameEntered(){
        lessonNameEntered.setValue(false);
    }

    private MutableLiveData<Boolean> lessonDurationEntered;
    public MutableLiveData<Boolean> getLessonDurationEntered() {
        if (lessonDurationEntered == null) {
            lessonDurationEntered = new MutableLiveData<Boolean>();
        }
        return lessonDurationEntered;
    }
    public void setTrueLessonDurationEntered(){ lessonDurationEntered.setValue(true); }
    public void setFalseLessonDurationEntered(){
        lessonDurationEntered.setValue(false);
    }

    private MutableLiveData<Boolean> lessonEditionDone;
    public MutableLiveData<Boolean> getLessonEditionDone() {
        if (lessonEditionDone == null) {
            lessonEditionDone = new MutableLiveData<Boolean>();
        }
        return lessonEditionDone;
    }
    public void setTrueLessonEditionDone(){ lessonEditionDone.setValue(true); }
    public void setFalseLessonEditionDone(){
        lessonEditionDone.setValue(false);
    }

    public void getEquipmentFromDatabase(ArrayList<Equipment> toPopulate)
    {
        //ScheduleDatabase.getInstance().scheduleDao()
        if(toPopulate.isEmpty())
        {
            toPopulate.add(new Equipment(1, "Książka"));
                    toPopulate.add(new Equipment(2, "Zeszyt"));
                            toPopulate.add(new Equipment(3, "Linijka"));
        }
    }
}
