package com.example.schoolscheduler.SharedViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.database.Lesson;

public class DayManagementLessonManagementVM extends ViewModel {

    private MutableLiveData<Lesson> chosenLesson;
    public MutableLiveData<Lesson> getChosenLesson() {
        if (chosenLesson == null) {
            chosenLesson = new MutableLiveData<>();
        }
        return chosenLesson;
    }
    public void setChosenLesson(Lesson newLesson){
        getChosenLesson().setValue(newLesson);
    }

  /*  private MutableLiveData<Boolean> workingOnExistingLesson;
    public MutableLiveData<Boolean> getWorkingOnExistingLesson(){
        if (workingOnExistingLesson == null) {
            workingOnExistingLesson = new MutableLiveData<>();
        }
        return workingOnExistingLesson;
    }
    public void setWorkingOnExistingLesson(Boolean toAssign){getWorkingOnExistingLesson().setValue(toAssign);}*/

  private boolean workingOnExistingLesson;
  public boolean getWorkingOnExistingLesson(){
        return workingOnExistingLesson;
}
public void setWorkingOnExistingLesson(boolean toAssign){
    workingOnExistingLesson = toAssign;
}
}
