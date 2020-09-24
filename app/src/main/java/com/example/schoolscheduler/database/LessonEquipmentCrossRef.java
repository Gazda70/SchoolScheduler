package com.example.schoolscheduler.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LessonEquipmentCrossRef",primaryKeys = {"lessonId", "eqId"})
public class LessonEquipmentCrossRef {
    public int lessonId;
    public int eqId;
}
