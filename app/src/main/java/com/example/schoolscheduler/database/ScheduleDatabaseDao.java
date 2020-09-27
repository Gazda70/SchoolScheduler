package com.example.schoolscheduler.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ScheduleDatabaseDao {

    @Insert
    Completable insert(Lesson newLesson);

    @Update
    Completable update(Lesson updateLesson);

    @Delete
    Completable delete(Lesson deleteLesson);

    @Query("SELECT * FROM table_schedule WHERE lessonId == :id")
    Flowable<Lesson> getLesson(int id);

    @Query("SELECT * FROM table_schedule WHERE lessonDay == :day ")
    Flowable<List<Lesson>> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period")
    Flowable<List<Lesson>> getLessonsForPeriod(String period);

    @Query("SELECT name FROM table_equipment WHERE eqId == :id")
    Flowable<String> getEqById(int id);

    @Transaction
    @Query("SELECT * FROM table_schedule")
    Flowable<List<LessonWithEquipment>> getLessonsWithEquipment();

    @Transaction
    @Query("SELECT * FROM table_equipment")
    Flowable<List<EquipmentWithLessons>> getEquipmentWithLessons();
}
