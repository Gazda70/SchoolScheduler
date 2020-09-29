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
        getGoEdit().setValue(true);
    }
    public void setFalseGoEdit(){
        getGoEdit().setValue(false);
    }

    private MutableLiveData<Boolean> goDays;
    public MutableLiveData<Boolean> getGoDays() {
        if (goDays == null) {
            goDays = new MutableLiveData<Boolean>();
        }
        return goDays;
    }
    public void setTrueGoDays(){ getGoDays().setValue(true); }
    public void setFalseGoDays(){
        getGoDays().setValue(false);
    }
    // TODO: Implement the ViewModel
}
