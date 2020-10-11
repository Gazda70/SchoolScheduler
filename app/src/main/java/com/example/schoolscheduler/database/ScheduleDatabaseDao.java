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

    @Insert
    void insertLesson(Lesson toInsert);
    @Insert
    void insertEquipment(Equipment insertEquipment);

    @Insert
    void insertLessonEquipmentCrossRef(LessonEquipmentCrossRef insertLECR);

    @Update
    void updateLesson(Lesson updateLesson);

    @Delete
    void deleteLesson(Lesson deleteLesson);

    //LESSON QUERIES
    @Query("SELECT * FROM table_schedule WHERE lessonName == :lessonName " +
            "AND lessonDay == :lessonDay AND lessonDuration == :lessonDuration")
    Lesson getLesson(String lessonName, String lessonDay, String lessonDuration);

    @Query("SELECT * FROM table_schedule WHERE lessonDay == :day ")
    List<Lesson> getLessonsForDay(String day);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period")
    List<Lesson> getLessonsForPeriod(String period);

   @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period AND lessonName = :name")
   List<Lesson> getLessonsForNameAndDay(String name, String period);

    @Query("SELECT * FROM table_schedule WHERE lessonDuration = :period AND lessonDay == :day")
    List<Lesson> getLessonsForPeriodAndDay(String day, String period);

   @Query("SELECT * FROM table_schedule")
   List<Lesson> testGetAllLessons();



   //EQUIPMENT QUERIES
    @Query("SELECT * FROM table_equipment WHERE eqId == :id")
    Equipment getEqById(int id);

    @Query("SELECT * FROM lesson_equipment_cross_ref WHERE lessonId = :lessonId")
    List<LessonEquipmentCrossRef> getLessonEqCrossRefForLessonId(int lessonId);

    @Transaction
    @Query("SELECT * FROM table_schedule WHERE lessonDay = :day")
    List<LessonWithEquipment> getLessonsWithEquipmentForDay(String day);

    @Transaction
    @Query("SELECT * FROM table_equipment")
    List<EquipmentWithLessons> getEquipmentWithLessons();

}
