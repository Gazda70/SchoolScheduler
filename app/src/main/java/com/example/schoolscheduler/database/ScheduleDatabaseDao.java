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

 /*   @Query("INSERT INTO table_schedule VALUES (:lessonName, :lessonDay, :lessonDuration)")
    void insertLesson(String lessonName,String lessonDay, String lessonDuration);*/
    @Insert
    void insertLesson(Lesson toInsert);
    @Insert
    void insertEquipment(Equipment insertEquipment);

    @Insert
    void insertLessonEquipmentCrossRef(LessonEquipmentCrossRef insertLECR);

    @Update
    void update(Lesson updateLesson);

    @Delete
    void delete(Lesson deleteLesson);

    @Query("SELECT * FROM table_schedule WHERE lessonName == :lessonName " +
            "AND lessonDay == :lessonDay AND lessonDuration == :lessonDuration")
    Lesson getLesson(String lessonName, String lessonDay, String lessonDuration);

    @Query("SELECT * FROM table_schedule WHERE lessonDay == :day ")
    List<Lesson> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period")
    LiveData<List<Lesson>> getLessonsForPeriod(String period);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period AND lessonDay == :day")
    LiveData<List<Lesson>> getLessonsForPeriodAndDay(String day, String period);

    @Query("SELECT * FROM table_equipment WHERE eqId == :id")
    LiveData<Equipment> getEqById(int id);

    @Query("SELECT * FROM lesson_equipment_cross_ref WHERE lessonId = :lessonId")
    LiveData<List<LessonEquipmentCrossRef>> getLessonEqCrossRefForLessonId(int lessonId);

    @Transaction
    @Query("SELECT * FROM table_schedule WHERE lessonDay = :day")
    LiveData<List<LessonWithEquipment>> getLessonsWithEquipmentForDay(String day);


    @Transaction
    @Query("SELECT * FROM table_equipment")
    LiveData<List<EquipmentWithLessons>> getEquipmentWithLessons();


    @Query("SELECT * FROM table_schedule")
    List<Lesson> testGetAllLessons();

}
