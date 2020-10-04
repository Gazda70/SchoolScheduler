package com.example.schoolscheduler;

import androidx.room.Room;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.schoolscheduler.database.ScheduleDatabase;
import com.example.schoolscheduler.database.ScheduleDatabaseDao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.rules.TestWatcher;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.*;

import static com.example.schoolscheduler.database.ScheduleDatabase.MIGRATION_1_2;


@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB = "migration-test";

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                Objects.requireNonNull(ScheduleDatabase.class.getCanonicalName()),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        db.execSQL("INSERT INTO table_schedule VALUES ('Matematyka', 'Poniedziałek', '8:00 - 8:45')," +
                "('J.polski', 'Poniedziałek', '8:55 - 9:40'), ('Geografia', 'Poniedziałek', '9:50 - 10:35')");

        // Prepare for the next version.
        db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);


        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.

        ScheduleDatabase testActualDatabase = Room.databaseBuilder(ApplicationProvider.getApplicationContext(),
                ScheduleDatabase.class, "scheduleDatabase").addMigrations(MIGRATION_1_2).build();

        ScheduleDatabaseDao testDao = testActualDatabase.scheduleDao();
        assertEquals("Matematyka",
                testDao.getLesson("Matematyka", "Poniedziałek", "8:00 - 8:45").lessonName);
    }

}
