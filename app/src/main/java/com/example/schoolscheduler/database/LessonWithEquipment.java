package com.example.schoolscheduler.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class LessonWithEquipment {
    @Embedded
    public Lesson lesson;
    @Relation(
            parentColumn = "lessonId",
            entityColumn = "eqId",
            associateBy = @Junction(LessonEquipmentCrossRef.class)
    )

    public List<Equipment> equipment;
}
