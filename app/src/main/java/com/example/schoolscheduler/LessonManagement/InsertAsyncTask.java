package com.example.schoolscheduler.LessonManagement;

import android.os.AsyncTask;
import android.util.Log;

import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.database.ScheduleDatabase;

import java.util.List;
import java.util.Objects;

public class InsertAsyncTask extends AsyncTask<Lesson, Void, Void> {

    private Lesson toInsert;

    InsertAsyncTask(Lesson toInsert){
        this.toInsert = toInsert;
    };

    @Override
    protected Void doInBackground(Lesson... lessons) {
        Log.i("INSERT ASYNC TASK", "SEE THIS CONTENT:");
        Log.i("FOR DAY:", toInsert.lessonDay);
       List<Lesson> list = ScheduleDatabase.getInstance().scheduleDao().testGetAllLessons();
        for(int i = 0; i < list.size(); i++){
            Log.i("IN DATABASE:", list.get(i).lessonName);
            Log.i("HAVING INDEX:", "" + list.get(i).lessonId);
            Log.i("FOR DAY:", list.get(i).lessonDay);
        }
      ScheduleDatabase.getInstance().scheduleDao().insertLesson(toInsert);
      List<Lesson> obtained = Objects.requireNonNull(ScheduleDatabase.getInstance().scheduleDao().getLessonsForDay(toInsert.lessonDay));
        Log.i("INSERTED:", obtained.get(obtained.size() - 1).lessonName);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
       Log.i("ASYNC TASK", "FINISHED");
    }
}
