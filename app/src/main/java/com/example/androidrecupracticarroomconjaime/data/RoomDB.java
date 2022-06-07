package com.example.androidrecupracticarroomconjaime.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Element.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB db;
    private static String DB_NAME = "base_de_datos_de_prueba";
    public abstract ElementDao mainDao();

    public synchronized static RoomDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DB_NAME)
                    .allowMainThreadQueries().build();
        }
        return db;
    }
}
