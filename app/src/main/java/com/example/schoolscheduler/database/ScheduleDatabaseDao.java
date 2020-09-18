package com.example.schoolscheduler.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface ScheduleDatabaseDao {

    @Insert
    void insert(Lesson newLesson);

    @Update
    void update(Lesson updateLesson);

    @Delete
    void delete(Lesson deleteLesson);

    @Query("SELECT * FROM table_schedule WHERE activityId == :lessonId")
    Lesson getLesson(int lessonId);

    @Query("SELECT * FROM table_schedule WHERE activityDay == :day ")
    LiveData<List<Lesson>> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE activityDuration = :period")
    LiveData<List<Lesson>> getLessonsForPeriod(String period);

    @Query("SELECT name FROM table_equipment WHERE eqId == :id")
    String getEqById(int id);
}
