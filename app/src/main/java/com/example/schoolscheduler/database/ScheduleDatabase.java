package com.example.schoolscheduler.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Lesson.class, Equipment.class}, version=1)
public abstract class ScheduleDatabase extends RoomDatabase {
    public abstract ScheduleDatabaseDao scheduleDao();


}
