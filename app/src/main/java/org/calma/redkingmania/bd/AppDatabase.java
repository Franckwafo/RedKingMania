package org.calma.redkingmania.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TokenSession.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract TokenSessionDao tokenSessionDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "bd_red_king_mania.db"
                    ).allowMainThreadQueries() // à éviter en prod, ok pour test
                    .build();
        }
        return INSTANCE;
    }
}
