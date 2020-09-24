package com.example.schoolscheduler.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class EquipmentWithLessons {
    @Embedded
    public Equipment equipment;
    @Relation(
            parentColumn = "eqId",
            entityColumn = "lessonId",
            associateBy = @Junction(LessonEquipmentCrossRef.class)
    )
    public List<Lesson> lessons;
}
