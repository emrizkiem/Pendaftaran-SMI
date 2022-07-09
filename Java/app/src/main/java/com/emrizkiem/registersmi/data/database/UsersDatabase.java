package com.emrizkiem.registersmi.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emrizkiem.registersmi.data.model.Users;

@Database(entities = {Users.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {
    /**
     * This method create database
     * @return
     */
    public abstract UsersDao usersDao();

    private static volatile UsersDatabase INSTANCE;

    public static UsersDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDatabase.class) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        UsersDatabase.class, "users_database"
                )
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.
                .allowMainThreadQueries()
                .build();
            }
        }

        return INSTANCE;
    }
}
