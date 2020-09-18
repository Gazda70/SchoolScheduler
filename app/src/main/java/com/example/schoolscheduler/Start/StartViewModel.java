package com.example.schoolscheduler.Start;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartViewModel extends ViewModel {

    private MutableLiveData<Boolean> goEdit;
    public MutableLiveData<Boolean> getGoEdit() {
        if (goEdit == null) {
            goEdit = new MutableLiveData<Boolean>();
        }
        return goEdit;
    }
    public void setTrueGoEdit(){
        goEdit.setValue(true);
    }
    public void setFalseGoEdit(){
        goEdit.setValue(false);
    }

    private MutableLiveData<Boolean> goDays;
    public MutableLiveData<Boolean> getGoDays() {
        if (goDays == null) {
            goDays = new MutableLiveData<Boolean>();
        }
        return goDays;
    }
    public void setTrueGoDays(){
        goDays.setValue(true);
    }
    public void setFalseGoDays(){
        goDays.setValue(false);
    }
    // TODO: Implement the ViewModel
}
