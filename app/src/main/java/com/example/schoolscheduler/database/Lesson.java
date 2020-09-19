package com.example.schoolscheduler.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_schedule")
public class Lesson {

    public Lesson(int activityId, String activityName, String activityDay, String activityDuration){
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityDay = activityDay;
        this.activityDuration = activityDuration;
    }

    @PrimaryKey
    public int activityId;

    @ColumnInfo
    public String activityName;

    @ColumnInfo
    public String activityDay;

    @ColumnInfo
    public String activityDuration;
}
