package com.example.schoolscheduler.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.schoolscheduler.ApplicationContextHolder;

@Database(entities = {Lesson.class, Equipment.class, LessonEquipmentCrossRef.class}, version=1)
public abstract class ScheduleDatabase extends RoomDatabase {
    public abstract ScheduleDatabaseDao scheduleDao();

    private static ScheduleDatabase instance;

    private static final String DB_NAME = "schedule_database";

    public static ScheduleDatabase getInstance(){
        if(instance == null){
            instance = buildScheduleDatabase(ApplicationContextHolder.getHeldApplicationContext());
        }
        return instance;
    }

    private static ScheduleDatabase buildScheduleDatabase(Context context){
        return Room.databaseBuilder(context,
                ScheduleDatabase.class, DB_NAME).build();
    }
}
