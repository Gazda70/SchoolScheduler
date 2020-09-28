package com.example.schoolscheduler.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ScheduleDatabaseDao {

    @Query("INSERT INTO table_schedule VALUES (:lessonId,:lessonName,:lessonDay,:lessonDuration)")
    void insert(int lessonId, String lessonName, String lessonDay, String lessonDuration);

    @Update
    void update(Lesson updateLesson);

    @Delete
    void delete(Lesson deleteLesson);

    @Query("SELECT * FROM table_schedule WHERE lessonId == :id")
    List<Lesson> getLesson(int id);

    @Query("SELECT * FROM table_schedule WHERE lessonDay == :day ")
    LiveData<List<Lesson>> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period")
    LiveData<List<Lesson>> getLessonsForPeriod(String period);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period AND lessonDay == :day")
    LiveData<List<Lesson>> getLessonsForPeriodAndDay(String day, String period);

    @Query("SELECT name FROM table_equipment WHERE eqId == :id")
    LiveData<String> getEqById(int id);

    @Transaction
    @Query("SELECT * FROM table_schedule")
    LiveData<List<LessonWithEquipment>> getLessonsWithEquipment();

    @Transaction
    @Query("SELECT * FROM table_equipment")
    LiveData<List<EquipmentWithLessons>> getEquipmentWithLessons();
}
