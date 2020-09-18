package com.example.schoolscheduler.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity(tableName = "table_schedule")
public class Lesson {
    @PrimaryKey
    public int activityId;

    @ColumnInfo
    public String activityName;

    @ColumnInfo
    public String activityDay;

    @ColumnInfo
    public String activityDuration;
}
