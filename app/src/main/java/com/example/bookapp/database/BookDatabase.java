package com.example.bookapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bookapp.model.BookEntity;
import com.example.bookapp.model.JournalEntryEntity;
import com.example.bookapp.utils.Converters;

@Database(entities = {BookEntity.class, JournalEntryEntity.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    public abstract JournalEntryDao journalEntryDao();
    private static volatile BookDatabase instance;

    public static BookDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    BookDatabase.class,
                    "app_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}