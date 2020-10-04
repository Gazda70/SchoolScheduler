package com.example.schoolscheduler.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.schoolscheduler.ApplicationContextHolder;

@Database(entities = {Lesson.class, Equipment.class, LessonEquipmentCrossRef.class}, version=2, exportSchema = true)
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
    @VisibleForTesting
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'table_schedule'" +
                    "('lessonId' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'lessonName' TEXT, 'lessonDay' TEXT, 'lessonDuration' TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS 'table_equipment' ('eqId' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'name' TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS 'lesson_equipment_cross_ref'" +
                    "('lessonId' INTEGER NOT NULL, 'eqId' INTEGER NOT NULL, PRIMARY KEY('lessonId', 'eqId'))");
        }
    };

}
