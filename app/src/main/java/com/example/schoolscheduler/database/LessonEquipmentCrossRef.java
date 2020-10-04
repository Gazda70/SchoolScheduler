package com.example.schoolscheduler.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lesson_equipment_cross_ref",primaryKeys = {"lessonId", "eqId"})
public class LessonEquipmentCrossRef {
    public int lessonId;
    public int eqId;
}
