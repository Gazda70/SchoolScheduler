package com.example.schoolscheduler.LessonManagement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;

import java.util.ArrayList;

public class LessonManagementViewModel extends ViewModel {

    private MutableLiveData<Boolean> addEquipment;
    public MutableLiveData<Boolean> getAddEquipment() {
        if (addEquipment == null) {
            addEquipment = new MutableLiveData<Boolean>();
        }
        return addEquipment;
    }
    public void setTrueAddLesson(){
        addEquipment.setValue(true);
    }
    public void setFalseAddLesson(){
        addEquipment.setValue(false);
    }

    public void getEquipmentFromDatabase(ArrayList<Equipment> toPopulate){

        if(toPopulate.isEmpty())
        {
            toPopulate.add(new Equipment(1, "Książka"));
                    toPopulate.add(new Equipment(2, "Zeszyt"));
                            toPopulate.add(new Equipment(3, "Linijka"));
        }
    }
}
