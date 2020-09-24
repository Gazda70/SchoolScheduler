package com.example.schoolscheduler.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_schedule")
public class Lesson {

    public Lesson(int lessonId, String lessonName, String lessonDay, String lessonDuration){
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.lessonDay = lessonDay;
        this.lessonDuration = lessonDuration;
    }

    @PrimaryKey
    public int lessonId;

    @ColumnInfo
    public String lessonName;

    @ColumnInfo
    public String lessonDay;

    @ColumnInfo
    public String lessonDuration;
}
