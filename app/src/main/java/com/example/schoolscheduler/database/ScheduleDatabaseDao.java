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

@Dao
public interface ScheduleDatabaseDao {

    @Insert
    void insert(Lesson newLesson);

    @Update
    void update(Lesson updateLesson);

    @Delete
    void delete(Lesson deleteLesson);

    @Query("SELECT * FROM table_schedule WHERE lessonId == :id")
    Lesson getLesson(int id);

    @Query("SELECT * FROM table_schedule WHERE lessonDay == :day ")
    LiveData<List<Lesson>> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period")
    LiveData<List<Lesson>> getLessonsForPeriod(String period);

    @Query("SELECT name FROM table_equipment WHERE eqId == :id")
    String getEqById(int id);

    @Transaction
    @Query("SELECT * FROM table_schedule")
    List<LessonWithEquipment> getLessonsWithEquipment();

    @Transaction
    @Query("SELECT * FROM table_equipment")
    List<EquipmentWithLessons> getEquipmentWithLessons();
}
